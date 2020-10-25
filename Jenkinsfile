pipeline {
  agent any
  stages {
    stage('Wrapper setup') {
      steps {
        sh 'chmod +x ./gradlew'
      }
    }

    stage('Build') {
      steps {
        sh './gradlew build'
      }
    }

    stage('Archive') {
      steps {
        archiveArtifacts 'build/libs/*.jar'
      }
    }

  }
}