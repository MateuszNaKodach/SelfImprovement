package com.github.nowakprojects.eventdrivenspringcloudstream

import java.time.Instant

data class UserNicknameChanged(val newNickname: String, val at: Instant)
