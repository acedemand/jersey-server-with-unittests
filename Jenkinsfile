pipeline {
  agent any
  stages {
    stage('checkout') {
      steps {
        git(url: 'https://github.com/mkyong/spring3-mvc-maven-xml-hello-world.git', branch: 'master')
      }
    }
  }
}