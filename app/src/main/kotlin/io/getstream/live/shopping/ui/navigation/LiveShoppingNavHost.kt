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

package io.getstream.live.shopping.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.viewmodel.channels.ChannelListViewModel
import io.getstream.chat.android.compose.viewmodel.channels.ChannelViewModelFactory
import io.getstream.chat.android.compose.viewmodel.messages.MessageComposerViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessageListViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessagesViewModelFactory
import io.getstream.chat.android.models.Filters
import io.getstream.chat.android.models.InitializationState
import io.getstream.chat.android.models.querysort.QuerySortByField
import io.getstream.live.shopping.ui.component.LiveShoppingLoadingIndicator
import io.getstream.live.shopping.ui.feature.channels.LiveShoppingChannels
import io.getstream.live.shopping.ui.feature.liveshopping.LiveShoppingScreen

@Composable
fun LiveShoppingNavHost(
  modifier: Modifier = Modifier,
  navController: NavHostController = rememberNavController(),
  startDestination: LiveShoppingScreen = LiveShoppingScreen.Channels
) {
  NavHost(
    modifier = modifier
      .fillMaxSize()
      .background(ChatTheme.colors.appBackground)
      .statusBarsPadding(),
    navController = navController,
    startDestination = startDestination
  ) {
    composable<LiveShoppingScreen.Channels> {
      val clientInitialisationState
        by ChatClient.instance().clientState.initializationState.collectAsState()

      if (clientInitialisationState == InitializationState.COMPLETE) {
        val factory = remember {
          ChannelViewModelFactory(
            chatClient = ChatClient.instance(),
            querySort = QuerySortByField.descByName("last_updated"),
            filters = Filters.eq("type", "livestream")
          )
        }

        val channelListViewModel: ChannelListViewModel = viewModel(factory = factory)

        LiveShoppingChannels(channelListViewModel = channelListViewModel)
      } else {
        LiveShoppingLoadingIndicator()
      }
    }

    composable<LiveShoppingScreen.LiveShopping> { backStackEntry ->
      val root: LiveShoppingScreen.LiveShopping = backStackEntry.toRoute()
      val context = LocalContext.current
      val factory = remember {
        MessagesViewModelFactory(
          context = context,
          channelId = root.cid,
          showDateSeparatorInEmptyThread = false,
          showSystemMessages = false
        )
      }

      val listViewModel: MessageListViewModel = viewModel(factory = factory)
      val composerViewModel: MessageComposerViewModel = viewModel(factory = factory)

      LiveShoppingScreen(
        isHost = root.isHost,
        listViewModel = listViewModel,
        composerViewModel = composerViewModel
      )
    }
  }
}
