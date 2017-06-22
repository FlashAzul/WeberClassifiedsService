# kill process if it is running
ps -A | grep "java"

# clean up the project
gradle clean

# build and start up the project
gradle bootrun
