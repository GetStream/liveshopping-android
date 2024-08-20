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

package io.getstream.live.shopping.chat

import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.models.Channel
import kotlin.random.Random

val Channel.streamerAvatarLink: String?
  inline get() = this.extraData[ChannelConst.EXTRA_STREAMER_AVATAR_LINK] as? String

val Channel.streamerName: String?
  inline get() = this.extraData[ChannelConst.EXTRA_STREAMER_NAME] as? String

val Channel.streamPreviewLink: String?
  inline get() = this.extraData[ChannelConst.EXTRA_STREAM_PREVIEW_LINK] as? String

val Channel.description: String?
  inline get() = this.extraData[ChannelConst.EXTRA_DESCRIPTION] as? String

val Channel.pointsIcon: String?
  inline get() = this.extraData[ChannelConst.EXTRA_POINTS_ICON] as? String

val Channel.pointsName: String?
  inline get() = this.extraData[ChannelConst.EXTRA_POINTS_NAME] as? String

@Suppress("UNCHECKED_CAST")
val Channel.tags: List<String>
  inline get() = this.extraData[ChannelConst.EXTRA_TAGS] as? List<String> ?: emptyList()

@PublishedApi
internal object ChannelConst {
  const val EXTRA_STREAMER_AVATAR_LINK = "EXTRA_STREAMER_AVATAR_LINK"
  const val EXTRA_STREAMER_NAME = "EXTRA_STREAMER_NAME"
  const val EXTRA_STREAM_PREVIEW_LINK = "EXTRA_STREAM_PREVIEW_LINK"
  const val EXTRA_DESCRIPTION = "EXTRA_DESCRIPTION"
  const val EXTRA_TAGS = "EXTRA_TAGS"
  const val EXTRA_POINTS_ICON = "EXTRA_POINTS_ICON"
  const val EXTRA_POINTS_NAME = "EXTRA_POINTS_NAME"
}

suspend fun ChatClient.createMockChannels() {
  createChannel(
    channelType = "livestream",
    channelId = "livestream${Random.nextInt(10000)}",
    memberIds = listOf(ChatClient.instance().getCurrentUser()?.id.orEmpty()),
    extraData = mockChannel1Extras
  ).await()

  createChannel(
    channelType = "livestream",
    channelId = "livestream${Random.nextInt(10000)}",
    memberIds = listOf(ChatClient.instance().getCurrentUser()?.id.orEmpty()),
    extraData = mockChannel2Extras
  ).await()
}

suspend fun ChatClient.createStreamerChannel(): Channel? {
  return createChannel(
    channelType = "livestream",
    channelId = "streamer",
    memberIds = listOf(ChatClient.instance().getCurrentUser()?.id.orEmpty()),
    extraData = mockChannel3Extras
  ).await().getOrNull()
}

private val mockChannel1Extras: Map<String, Any>
  inline get() = mapOf(
    ChannelConst.EXTRA_STREAMER_AVATAR_LINK to "https://placekitten.com/200/300",
    ChannelConst.EXTRA_STREAMER_NAME to "City Items",
    ChannelConst.EXTRA_STREAM_PREVIEW_LINK to "https://github.com/user-attachments/assets/" +
      "f1da7897-c7c9-41c2-8f3c-3358cf490696",
    ChannelConst.EXTRA_DESCRIPTION to "Come to watch the products that you can get a lot of discount!",
    ChannelConst.EXTRA_TAGS to listOf("Shopping", "City", "Discount", "City Views", "Night Views"),
    ChannelConst.EXTRA_POINTS_ICON to "https://cdn.betterttv.net/emote/60ee7ce38ed8b373e4222366/1x",
    ChannelConst.EXTRA_POINTS_NAME to "Spider"
  )

private val mockChannel2Extras: Map<String, Any>
  inline get() = mapOf(
    ChannelConst.EXTRA_STREAMER_AVATAR_LINK to "https://placekitten.com/200/300",
    ChannelConst.EXTRA_STREAMER_NAME to "Handsome Suit",
    ChannelConst.EXTRA_STREAM_PREVIEW_LINK to "https://github.com/user-attachments/assets/" +
      "4d59d695-1c5a-4edb-b26a-4fe1f1f71928",
    ChannelConst.EXTRA_DESCRIPTION to "Come watch an awesome suits!",
    ChannelConst.EXTRA_TAGS to listOf("Live", "Cloth", "Suits", "Man", "Gentle"),
    ChannelConst.EXTRA_POINTS_ICON to "https://cdn.betterttv.net/emote/60ee7ce38ed8b373e4222366/1x",
    ChannelConst.EXTRA_POINTS_NAME to "Buns"
  )

private val mockChannel3Extras: Map<String, Any>
  inline get() = mapOf(
    ChannelConst.EXTRA_STREAMER_AVATAR_LINK to "https://placekitten.com/200/300",
    ChannelConst.EXTRA_STREAMER_NAME to "Streamer",
    ChannelConst.EXTRA_STREAM_PREVIEW_LINK to "https://github.com/user-attachments/assets/" +
      "3ae6e3c5-b1fc-4a5f-9774-33dc724d0d74",
    ChannelConst.EXTRA_DESCRIPTION to "Come watch an awesome fruits!",
    ChannelConst.EXTRA_TAGS to listOf("Livestreaming", "Fruits", "Sales", "Discount", "Apple"),
    ChannelConst.EXTRA_POINTS_ICON to "https://cdn.betterttv.net/emote/60ee7ce38ed8b373e4222366/1x",
    ChannelConst.EXTRA_POINTS_NAME to "Buns"
  )
