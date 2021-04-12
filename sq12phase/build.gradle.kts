import configurations.Languages.attachRemoteRepositories
import configurations.Languages.configureJava
import configurations.Publications.configureMavenPublication
import dependencies.Libraries.LOGBACK_CLASSIC

description = "A copy of Chen Shuang's square 1 two phase solver."

plugins {
    `java-library`
    `maven-publish`
}

configureJava()
configureMavenPublication("scrambler-sq12phase")

attachRemoteRepositories()

dependencies {
    implementation(LOGBACK_CLASSIC)
}
