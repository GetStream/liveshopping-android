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

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.getstream.video.android.core.Call
import io.getstream.video.android.core.StreamVideo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LiveShoppingViewModel @Inject constructor() : ViewModel() {

  private val liveShoppingUiStateMutableState =
    MutableStateFlow<LiveShoppingUiState>(LiveShoppingUiState.Loading)
  val liveShoppingUiState: StateFlow<LiveShoppingUiState> = liveShoppingUiStateMutableState

  suspend fun joinCall(type: String, id: String) {
    val streamVideo = StreamVideo.instance()
    val activeCall = streamVideo.state.activeCall.value
    val call = if (activeCall != null) {
      if (activeCall.id != id) {
        // If the call id is different leave the previous call
        activeCall.leave()
        // Return a new call
        streamVideo.call(type = type, id = id)
      } else {
        // Call ID is the same, use the active call
        activeCall
      }
    } else {
      // There is no active call, create new call
      streamVideo.call(type = type, id = id)
    }
    val result = call.join(create = true)
    result.onSuccess {
      liveShoppingUiStateMutableState.value = LiveShoppingUiState.Success(call)
    }.onError {
      liveShoppingUiStateMutableState.value = LiveShoppingUiState.Error
    }
  }

  override fun onCleared() {
    super.onCleared()
  }
}

sealed interface LiveShoppingUiState {

  data object Loading : LiveShoppingUiState

  data class Success(val call: Call) : LiveShoppingUiState

  data object Error : LiveShoppingUiState
}
