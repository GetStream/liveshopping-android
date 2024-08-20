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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.glide.GlideImage
import com.skydoves.landscapist.placeholder.shimmer.Shimmer
import com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin
import io.getstream.chat.android.compose.ui.components.composer.InputField
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.viewmodel.messages.MessageComposerViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessageListViewModel
import io.getstream.chat.android.models.Channel
import io.getstream.chat.android.models.Message
import io.getstream.chat.android.ui.common.state.messages.list.MessageItemState
import io.getstream.live.shopping.R
import io.getstream.live.shopping.chat.ChannelConst.EXTRA_DESCRIPTION
import io.getstream.live.shopping.chat.ChannelConst.EXTRA_STREAMER_NAME
import io.getstream.live.shopping.chat.ChannelConst.EXTRA_STREAM_PREVIEW_LINK
import io.getstream.live.shopping.ui.component.StreamButton
import io.getstream.live.shopping.ui.theme.shimmerBase
import io.getstream.live.shopping.ui.theme.shimmerHighlight
import io.getstream.video.android.compose.theme.VideoTheme

@Composable
internal fun LiveShoppingBottomBar(
  messages: List<MessageItemState>,
  listViewModel: MessageListViewModel,
  composerViewModel: MessageComposerViewModel,
) {
  Column(
    modifier = Modifier
      .padding(horizontal = 6.dp)
      .navigationBarsPadding()
      .fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(6.dp)
  ) {

    ChatOverly(messages = messages)

    ProductBanner(channel = listViewModel.channel)

    ChatInput(
      cid = listViewModel.channel.cid,
      messageComposerViewModel = composerViewModel
    )
  }
}

@Composable
private fun ProductBanner(
  channel: Channel,
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .background(
        color = ChatTheme.colors.appBackground.copy(alpha = 0.85f),
        shape = RoundedCornerShape(16.dp)
      )
      .padding(12.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    GlideImage(
      modifier = Modifier
        .size(76.dp)
        .clip(RoundedCornerShape(16.dp)),
      imageModel = { channel.extraData[EXTRA_STREAM_PREVIEW_LINK].toString() },
      component = rememberImageComponent {
        +ShimmerPlugin(
          Shimmer.Resonate(
            baseColor = shimmerBase,
            highlightColor = shimmerHighlight
          )
        )
      }
    )

    Column(
      modifier = Modifier.padding(6.dp),
      verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
      Text(
        text = channel.extraData[EXTRA_STREAMER_NAME].toString(),
        color = ChatTheme.colors.textHighEmphasis,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
      )

      Text(
        text = channel.extraData[EXTRA_DESCRIPTION].toString(),
        color = ChatTheme.colors.textLowEmphasis,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
      )

      Text(
        text = "$79.99",
        color = VideoTheme.colors.brandRed,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
      )
    }

    StreamButton(
      modifier = Modifier
        .padding(start = 16.dp)
        .height(36.dp),
      text = stringResource(R.string.buy),
      onClick = {},
    )
  }
}

@Composable
private fun ChatInput(
  cid: String,
  messageComposerViewModel: MessageComposerViewModel
) {

  val (text, changeText) = remember { mutableStateOf("") }

  Row(
    modifier = Modifier
      .imePadding()
      .fillMaxWidth()
      .padding(bottom = 12.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Icon(
      imageVector = Icons.Default.ShoppingBag,
      tint = VideoTheme.colors.brandYellow,
      contentDescription = null
    )

    InputField(
      modifier = Modifier
        .padding(horizontal = 6.dp)
        .weight(1f)
        .height(36.dp),
      value = text,
      onValueChange = changeText,
      innerPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
      decorationBox = { innerTextField ->
        innerTextField()
      }
    )

    Icon(
      modifier = Modifier.clickable(enabled = text.isNotEmpty()) {
        messageComposerViewModel.sendMessage(
          message = Message(
            cid = cid,
            text = text
          )
        )
        changeText.invoke("")
      },
      imageVector = Icons.Default.Send,
      tint = if (text.isNotEmpty()) {
        VideoTheme.colors.brandPrimary
      } else {
        VideoTheme.colors.iconDisabled
      },
      contentDescription = null
    )
  }
}
