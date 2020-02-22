import configurations.Languages.attachRemoteRepositories
import configurations.Languages.configureJava
import configurations.Publications.configureBintrayTarget
import configurations.Publications.configureMavenPublication
import dependencies.Libraries.LOGBACK_CLASSIC

description = "A copy of Chen Shuang's 5x5 scrambler."

plugins {
    `java-library`
    `maven-publish`
    JFROG_BINTRAY
}

configureJava()
configureMavenPublication("scrambler-cube555")
configureBintrayTarget()

attachRemoteRepositories()

dependencies {
    implementation(project(":min2phase"))
    implementation(LOGBACK_CLASSIC)
}
