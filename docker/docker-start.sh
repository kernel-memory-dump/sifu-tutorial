#!/bin/bash

PROFILE=$1

if [ "$PROFILE" = "" ]; then
  echo "Please choose run profile for docker-compose - [dev], prod, test:"
  read PROFILE
fi

if [ "$PROFILE" = "" ] || [ "$PROFILE" = "dev" ]; then
	docker-compose -f dev.yml up
elif [ "$PROFILE" = "prod" ]; then
  docker-compose -f prod.yml up
elif [ "$PROFILE" = "test" ]; then
  docker-compose -f test.yml up
else
  echo "Wrong argument."
fi