#!/usr/bin/env bash
set -euo pipefail
BASE=http://localhost:8080
META=./test-data/meta.json
REP=./test-data/mypackage.rep

echo "1) Deploy:"
curl -s -o /dev/null -w "%{http_code}\n" \
  -F "package.rep=@${REP}" \
  -F "meta.json=@${META}" \
  ${BASE}/mypkg/1.0.0 | grep -q 201 && echo "✔ 201"

echo "2) Download:"
curl -s -o downloaded.rep ${BASE}/mypkg/1.0.0/package.rep
cmp --silent downloaded.rep ${REP} && echo "✔ içerik aynı"

echo "3) Negatif test:"
curl -s -o /dev/null -w "%{http_code}\n" \
  ${BASE}/nope/0.0.1/meta.json | grep -q 404 && echo "✔ 404"

echo "Tüm testler geçti!"
