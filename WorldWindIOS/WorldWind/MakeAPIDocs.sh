#!/bin/sh
# @version $Id: MakeAPIDocs.sh 2354 2014-09-30 18:08:09Z tgaskins $
# The below command line installs the World Wind iOS documentation in the Xcode documentation system.
appledoc --project-name "NASA World Wind" --project-company "NASA World Wind" --company-id gov.nasa.worldwind --output ~/help .