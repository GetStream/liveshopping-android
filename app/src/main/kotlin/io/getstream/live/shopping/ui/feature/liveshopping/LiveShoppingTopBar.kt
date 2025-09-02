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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PausePresentation
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.getstream.chat.android.compose.viewmodel.messages.MessageComposerViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessageListViewModel
import io.getstream.live.shopping.R
import io.getstream.live.shopping.chat.streamerAvatarLink
import io.getstream.live.shopping.chat.streamerName
import io.getstream.video.android.compose.theme.VideoTheme
import io.getstream.video.android.core.Call
import kotlinx.coroutines.launch

@Composable
internal fun LiveShoppingTopBar(
  call: Call,
  isHost: Boolean,
  listViewModel: MessageListViewModel,
  composerViewModel: MessageComposerViewModel
) {
  val context = LocalContext.current
  val backstage by call.state.backstage.collectAsState()
  val totalParticipants by call.state.totalParticipants.collectAsState()
  val scope = rememberCoroutineScope()

  if (isHost) {
    if (backstage) {
      call.microphone.setEnabled(false, true)
    } else {
      call.microphone.setEnabled(true, true)
    }
  }

  Box(
    modifier = Modifier
      .fillMaxWidth()
      .padding(6.dp)
  ) {
    LiveShoppingHostInformation(
      isHost = isHost,
      modifier = Modifier.align(Alignment.CenterStart),
      streamerAvatarImage = listViewModel.channel.streamerAvatarLink,
      streamerName = listViewModel.channel.streamerName,
      totalParticipants = totalParticipants
    )

    if (isHost) {
      Button(
        modifier = Modifier.align(Alignment.TopEnd),
        colors = if (backstage) {
          ButtonDefaults.buttonColors(
            contentColor = VideoTheme.colors.brandYellow,
            containerColor = VideoTheme.colors.brandYellow
          )
        } else {
          ButtonDefaults.buttonColors(
            contentColor = VideoTheme.colors.brandMaroon,
            containerColor = VideoTheme.colors.brandMaroon
          )
        },
        onClick = {
          scope.launch {
            if (backstage) {
              call.goLive()

              val message = composerViewModel.buildNewMessage(
                message = context.getString(R.string.livestream_live_started)
              )

              composerViewModel.sendMessage(message)
            } else {
              call.stopLive()

              val message = composerViewModel.buildNewMessage(
                message = context.getString(R.string.livestream_live_end)
              )
              composerViewModel.sendMessage(message)
            }
          }
        }
      ) {
        if (backstage) {
          Icon(
            imageVector = Icons.Default.PlayArrow,
            tint = Color.White,
            contentDescription = null
          )
        } else {
          Icon(
            imageVector = Icons.Default.PausePresentation,
            tint = Color.White,
            contentDescription = null
          )
        }

        Spacer(modifier = Modifier.width(6.dp))

        Text(
          text = if (backstage) {
            stringResource(id = R.string.livestream_go_live)
          } else {
            stringResource(id = R.string.livestream_go_backstage)
          },
          color = Color.White
        )
      }
    }
  }
}
