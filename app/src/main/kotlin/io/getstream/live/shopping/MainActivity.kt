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

package io.getstream.live.shopping

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import io.getstream.live.shopping.ui.navigation.LiveShoppingNavHost
import io.getstream.live.shopping.ui.navigation.LiveShoppingNavigator
import io.getstream.live.shopping.ui.navigation.LocalComposeNavigator
import io.getstream.live.shopping.ui.theme.LiveShoppingTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    setContent {
      val appComposeNavigator = remember { LiveShoppingNavigator() }
      val navHostController = rememberNavController()

      LaunchedEffect(Unit) {
        appComposeNavigator.handleNavigationCommands(navHostController)
      }

      CompositionLocalProvider(
        LocalComposeNavigator provides appComposeNavigator
      ) {
        LiveShoppingTheme {
          LiveShoppingNavHost(navController = navHostController)
        }
      }
    }
  }
}
