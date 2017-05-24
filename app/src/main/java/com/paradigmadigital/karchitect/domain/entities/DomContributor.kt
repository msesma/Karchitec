/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.paradigmadigital.karchitect.domain.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey

@Entity(primaryKeys = arrayOf("repoName", "repoOwner", "login"),
        foreignKeys = arrayOf(ForeignKey(
                entity = DomRepo::class,
                parentColumns = arrayOf("name", "owner_login"),
                childColumns = arrayOf("repoName", "repoOwner"),
                onUpdate = ForeignKey.CASCADE,
                deferred = true)))
class DomContributor {

    private val login: String? = null

    private val contributions: Int = 0

    private val avatarUrl: String? = null

    private val repoName: String? = null

    private val repoOwner: String? = null
}
