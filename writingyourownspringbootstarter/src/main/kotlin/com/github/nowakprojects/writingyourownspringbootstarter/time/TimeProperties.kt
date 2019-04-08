package com.github.nowakprojects.writingyourownspringbootstarter.time

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * Properties for application time
 */
@ConfigurationProperties("time")
internal class TimeProperties {

    /**
     * Indicate if time of the application should be fixed during whole runtime.
     */
    var fixed: Boolean = false
}