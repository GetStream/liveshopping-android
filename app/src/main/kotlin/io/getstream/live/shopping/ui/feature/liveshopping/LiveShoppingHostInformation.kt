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

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.live.shopping.R
import io.getstream.live.shopping.ui.component.StreamButton
import io.getstream.live.shopping.ui.preview.DefaultPreview
import io.getstream.video.android.compose.theme.VideoTheme

@Composable
fun LiveShoppingHostInformation(
  isHost: Boolean,
  totalParticipants: Int,
  streamerAvatarImage: String?,
  streamerName: String?,
  modifier: Modifier = Modifier,
  verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
) {
  Column {
    Row(
      modifier = modifier
        .background(
          color = Color.Black.copy(alpha = 0.45f),
          shape = RoundedCornerShape(32.dp)
        )
        .padding(vertical = 4.dp, horizontal = 6.dp),
      verticalAlignment = verticalAlignment,
      horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
      GlideImage(
        modifier = Modifier
          .size(24.dp)
          .clip(CircleShape),
        imageModel = { streamerAvatarImage },
        imageOptions = ImageOptions(contentScale = ContentScale.Crop),
        previewPlaceholder = painterResource(R.drawable.ic_launcher_foreground),
      )

      Column(modifier = Modifier.padding(end = 6.dp)) {
        if (streamerName != null) {
          Text(
            modifier = Modifier.wrapContentSize(),
            text = streamerName,
            style = ChatTheme.typography.bodyBold,
            fontSize = 14.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
          )
        }

        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
          Icon(
            modifier = Modifier.size(14.dp),
            imageVector = Icons.Default.ThumbUp,
            tint = Color.White,
            contentDescription = null
          )

          Text(
            modifier = Modifier.wrapContentSize(),
            text = "4.6k",
            style = ChatTheme.typography.bodyBold,
            fontSize = 14.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
          )
        }
      }

      if (!isHost) {
        StreamButton(
          modifier = Modifier.height(32.dp),
          text = stringResource(R.string.follow),
          onClick = {}
        )
      }
    }

    LiveBadge(totalParticipants = totalParticipants)
  }
}

@Composable
private fun LiveBadge(totalParticipants: Int) {
  Row(
    modifier = Modifier
      .padding(vertical = 6.dp)
      .background(
        color = Color.Black.copy(alpha = 0.45f),
        shape = RoundedCornerShape(32.dp)
      )
      .padding(horizontal = 6.dp, vertical = 4.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Text(
      modifier = Modifier
        .background(
          color = VideoTheme.colors.brandRed,
          shape = VideoTheme.shapes.container,
        )
        .padding(horizontal = 16.dp, vertical = 4.dp),
      text = stringResource(
        id = io.getstream.video.android.ui.common.R.string.stream_video_live,
      ),
      color = Color.White,
    )

    Image(
      modifier = Modifier
        .size(20.dp)
        .padding(start = 6.dp, end = 2.dp),
      painter = painterResource(
        id = io.getstream.video.android.ui.common.R.drawable.stream_video_ic_live,
      ),
      contentDescription = stringResource(
        id = io.getstream.video.android.ui.common.R.string.stream_video_live,
      ),
    )

    Text(
      text = totalParticipants.toString(),
      color = Color.White,
      fontSize = 13.sp,
      fontWeight = FontWeight.Bold,
    )
  }
}

@Composable
@DefaultPreview
private fun LiveShoppingTopBarPreview() {
  ChatTheme {
    LiveShoppingHostInformation(
      streamerAvatarImage = null,
      streamerName = "streamer",
      totalParticipants = 20,
      isHost = true,
    )
  }
}
