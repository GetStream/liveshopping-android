/*
 * Copyright 2024 Stream.IO, Inc. All Rights Reserved.
 *
 * Licensed under the Stream License;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.getstream.live.shopping.ui.feature.liveshopping

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.viewmodel.messages.MessageComposerViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessageListViewModel
import io.getstream.chat.android.ui.common.state.messages.list.MessageItemState
import io.getstream.live.shopping.R
import io.getstream.video.android.compose.permission.LaunchCallPermissions
import io.getstream.video.android.compose.ui.components.livestream.LivestreamPlayer
import io.getstream.video.android.compose.ui.components.video.VideoRenderer
import io.getstream.video.android.core.Call
import kotlinx.coroutines.launch

@Composable
fun LiveShoppingScreen(
  isHost: Boolean,
  listViewModel: MessageListViewModel,
  composerViewModel: MessageComposerViewModel,
  livestreamViewModel: LiveShoppingViewModel = hiltViewModel()
) {
  val uiState by livestreamViewModel.liveShoppingUiState.collectAsState()

  val scope = rememberCoroutineScope()
  EnsureVideoCallPermissions {
    scope.launch {
      livestreamViewModel.joinCall(type = "livestream", id = "streamer")
    }
  }

  DisposableEffect(Unit) {
    onDispose {
      val call = (uiState as? LiveShoppingUiState.Success)?.call
      call?.leave()
    }
  }

  LivestreamStreamerContent(
    isHost = isHost,
    listViewModel = listViewModel,
    composerViewModel = composerViewModel,
    uiState = uiState
  )
}

@Composable
private fun LivestreamStreamerContent(
  isHost: Boolean,
  listViewModel: MessageListViewModel,
  composerViewModel: MessageComposerViewModel,
  uiState: LiveShoppingUiState
) {
  when (uiState) {
    is LiveShoppingUiState.Success -> {
      StreamRenderer(
        modifier = Modifier.fillMaxSize(),
        call = uiState.call,
        isHost = isHost,
        listViewModel = listViewModel,
        composerViewModel = composerViewModel
      )
    }

    LiveShoppingUiState.Error -> {
      Box(
        modifier = Modifier
          .fillMaxSize()
          .background(ChatTheme.colors.appBackground)
      ) {
        Text(
          modifier = Modifier.align(Alignment.Center),
          text = stringResource(id = R.string.livestream_joining_failed),
          color = ChatTheme.colors.textHighEmphasis,
          fontSize = 12.sp
        )
      }
    }

    LiveShoppingUiState.Loading -> {
      Box(
        modifier = Modifier
          .fillMaxSize()
          .background(ChatTheme.colors.appBackground)
      ) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
      }
    }
  }
}

@Composable
private fun StreamRenderer(
  modifier: Modifier,
  call: Call,
  isHost: Boolean,
  listViewModel: MessageListViewModel,
  composerViewModel: MessageComposerViewModel
) {
  LaunchCallPermissions(call = call)

  val localParticipant by call.state.localParticipant.collectAsState()
  val video = localParticipant?.video?.collectAsState()?.value
  val messages = listViewModel.currentMessagesState.value.messageItems
    .filterIsInstance<MessageItemState>().take(5)

  Scaffold(
    modifier = modifier.background(ChatTheme.colors.appBackground),
    contentColor = ChatTheme.colors.appBackground,
    containerColor = ChatTheme.colors.appBackground,
    topBar = {
      LiveShoppingTopBar(
        call = call,
        isHost = isHost,
        listViewModel = listViewModel,
        composerViewModel = composerViewModel
      )
    },
    bottomBar = {
      LiveShoppingBottomBar(
        messages = messages,
        listViewModel = listViewModel,
        composerViewModel = composerViewModel
      )
    }
  ) {
    if (isHost) {
      VideoRenderer(
        modifier = Modifier
          .fillMaxSize()
          .padding(
            start = it.calculateStartPadding(
              LayoutDirection.Ltr
            ),
            end = it.calculateEndPadding(LayoutDirection.Ltr)
          )
          .clip(RoundedCornerShape(6.dp)),
        call = call,
        video = video,
        videoFallbackContent = {
          Text(stringResource(id = R.string.livestream_rendering_failed))
        }
      )
    } else {
      LivestreamPlayer(call = call, overlayContent = {})
    }
  }
}
