#!/bin/bash

#######################################
# The script starts Chrome, Edge or Firefox browser in a Docker container for running browser tests locally.
# The container directs TCP requests from the container to the host machine enabling testing services running via Service Manager.
# WARNING: Do not use this script in the Jenkins Continuous Integration environment.
#
# Arguments:
#   chrome, edge or firefox
#
# Output:
#   Starts Chrome, Edge or Firefox browser in a Docker container based on chrome-with-rinetd, edge-with-rinetd or firefox-with-rinetd image respectively.
#######################################

#######################################
# Requires services under test running via Service Manager.
# Initializes port_mappings with all running application ports using the Service Manager status command.
# Appends ZAP_PORT 11000 to ./run-zap-spec.sh.
#######################################
port_mappings=$(sm2 --status | grep PASS | awk '{ print $8"->"$8 }' | paste -sd "," -)
port_mappings="$port_mappings,11000->11000"

# Alternatively, port_mappings can be explicitly initialised as below:
#port_mappings="9032->9032,9250->9250,9080->9080"

#######################################
# Defines the BROWSER variable from the argument passed to the script.
#######################################
if [ -z "${1}" ]; then
  echo "ERROR: Browser type not specified. Re-run the script with the option chrome, edge or firefox."
  exit 1
elif [ "${1}" = "chrome" ]; then
  BROWSER="artefacts.tax.service.gov.uk/chrome-with-rinetd:latest"
elif [ "${1}" = "edge" ]; then
  BROWSER="artefacts.tax.service.gov.uk/edge-with-rinetd:latest"
elif [ "${1}" = "firefox" ]; then
  BROWSER="artefacts.tax.service.gov.uk/firefox-with-rinetd:latest"
fi

#######################################
# Pulls the BROWSER image from artifactory and runs the Docker container with the specified options.
#
# Accepted Environment Variables:
# PORT_MAPPINGS: List of the ports of the services under test.
# TARGET_IP: IP of the host machine. For Mac this is 'host.docker.internal'. For linux this is 'localhost'.
#
# The latest version of the docker images are available at:
# https://artefacts.tax.service.gov.uk/artifactory/webapp/#/artifacts/browse/tree/General/chrome-with-rinetd
# https://artefacts.tax.service.gov.uk/artifactory/webapp/#/artifacts/browse/tree/General/edge-with-rinetd
# https://artefacts.tax.service.gov.uk/artifactory/webapp/#/artifacts/browse/tree/General/firefox-with-rinetd
#
# NOTE:
# When using on a Linux OS, add "--net=host" to the docker run command.
#######################################

docker pull ${BROWSER} \
  && docker run \
    -d \
    --rm \
    --name "${1}" \
    --shm-size=2g \
    -p 4444:4444 \
    -p 5900:5900 \
    -e PORT_MAPPINGS="$port_mappings" \
    -e TARGET_IP='host.docker.internal' \
    ${BROWSER}
