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

import androidx.navigation.NavOptions

sealed interface NavigationCommand {
  data object NavigateUp : NavigationCommand
}

sealed interface ComposeNavigationCommand : NavigationCommand {
  data class NavigateToRoute<T : Any>(val route: T, val options: NavOptions? = null) :
    ComposeNavigationCommand

  data class NavigateUpWithResult<R, T : Any>(
    val key: String,
    val result: R,
    val route: T? = null,
  ) : ComposeNavigationCommand

  data class PopUpToRoute<T : Any>(val route: T, val inclusive: Boolean) :
    ComposeNavigationCommand
}
