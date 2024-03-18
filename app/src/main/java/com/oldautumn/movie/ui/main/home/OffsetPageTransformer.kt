/*
 * Copyright 2023 The Old Autumn Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.oldautumn.movie.ui.main.home

import android.view.View
import android.view.ViewGroup
import androidx.annotation.Px
import androidx.core.view.ViewCompat
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

class OffsetPageTransformer(
    @Px private val offsetPx: Int,
    @Px private val pageMarginPx: Int
) : ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        val viewPager = requireViewPager(page)
        val offset = position * (-(2 * offsetPx + pageMarginPx))
        val totalMargin = offsetPx + pageMarginPx

        if (viewPager.orientation == ViewPager2.ORIENTATION_HORIZONTAL) {
            page.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                marginStart = totalMargin
                marginEnd = totalMargin
            }

            page.translationX =
                if (ViewCompat.getLayoutDirection(viewPager) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                    -offset
                } else {
                    offset
                }
        } else {
            page.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                topMargin = totalMargin
                bottomMargin = totalMargin
            }

            page.translationY = offset
        }
    }

    private fun requireViewPager(page: View): ViewPager2 {
        val parent = page.parent
        val parentParent = parent.parent
        if (parent is RecyclerView && parentParent is ViewPager2) {
            return parentParent
        }
        throw IllegalStateException(
            "Expected the page view to be managed by a ViewPager2 instance."
        )
    }
}
