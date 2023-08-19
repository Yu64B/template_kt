#!/usr/bin/bash
unexpand -t 4 --first-only | sed -e 's/\t/\\t/g' -e 's/"/\\"/g' -e 's/\$/\\\\$/g' -e 's/^/"/g' -e 's/$/",/g'
