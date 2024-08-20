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

package io.getstream.live.shopping.ui.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.live.shopping.ui.preview.DefaultPreview

@Composable
fun StreamButton(
  modifier: Modifier = Modifier,
  text: String,
  contentColor: Color = ChatTheme.colors.primaryAccent,
  enabled: Boolean = true,
  onClick: () -> Unit
) {
  Button(
    modifier = modifier.clip(RoundedCornerShape(8.dp)),
    enabled = enabled,
    onClick = onClick,
    colors = ButtonDefaults.buttonColors(
      containerColor = contentColor,
      contentColor = contentColor
    )
  ) {
    Text(
      text = text,
      color = Color.White
    )
  }
}

@DefaultPreview
@Composable
private fun StreamButtonPreview() {
  ChatTheme {
    StreamButton(text = "Sign In with Email", onClick = {})
  }
}
