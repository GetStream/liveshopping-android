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

package io.getstream.live.shopping.ui.feature.channels

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.glide.GlideImage
import com.skydoves.landscapist.placeholder.shimmer.Shimmer
import com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin
import io.getstream.chat.android.compose.state.channels.list.ItemState
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.live.shopping.R
import io.getstream.live.shopping.chat.description
import io.getstream.live.shopping.chat.streamPreviewLink
import io.getstream.live.shopping.chat.streamerAvatarLink
import io.getstream.live.shopping.chat.streamerName
import io.getstream.live.shopping.chat.tags
import io.getstream.live.shopping.ui.navigation.LiveShoppingScreen
import io.getstream.live.shopping.ui.navigation.currentComposeNavigator
import io.getstream.live.shopping.ui.theme.shimmerBase
import io.getstream.live.shopping.ui.theme.shimmerHighlight

@Composable
fun LiveShoppingChannelItem(
  channelItemState: ItemState.ChannelItemState
) {
  val navigator = currentComposeNavigator

  Column(
    modifier = Modifier
      .fillMaxWidth()
      .clickable {
        navigator.navigate(
          LiveShoppingScreen.LiveShopping(
            cid = channelItemState.channel.cid,
            isHost = false
          )
        )
      }
  ) {
    Box(
      modifier = Modifier.fillMaxWidth()
    ) {
      GlideImage(
        modifier = Modifier
          .fillMaxWidth()
          .height(260.dp)
          .padding(vertical = 8.dp, horizontal = 12.dp)
          .clip(RoundedCornerShape(6.dp)),
        imageModel = { channelItemState.channel.streamPreviewLink },
        component = rememberImageComponent {
          +ShimmerPlugin(
            Shimmer.Resonate(
              baseColor = shimmerBase,
              highlightColor = shimmerHighlight
            )
          )
        }
      )

      Text(
        modifier = Modifier
          .padding(vertical = 14.dp, horizontal = 18.dp)
          .background(
            color = ChatTheme.colors.errorAccent,
            shape = RoundedCornerShape(4.dp)
          )
          .padding(horizontal = 6.dp)
          .align(Alignment.TopStart),
        text = stringResource(id = R.string.label_live),
        color = ChatTheme.colors.textHighEmphasis,
        fontSize = 12.sp
      )
    }

    ChannelInformation(
      modifier = Modifier
        .background(ChatTheme.colors.appBackground)
        .fillMaxWidth(),
      streamerAvatarImage = channelItemState.channel.streamerAvatarLink,
      streamerName = channelItemState.channel.streamerName,
      channelDescription = channelItemState.channel.description,
      channelTags = channelItemState.channel.tags
    )
  }
}
