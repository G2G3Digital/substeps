#! /bin/bash

# build the latest set of docs

cd docs
make html
cd ..

# move the example feature report into place
cp -R example_substeps_report docs/_build/html/_static

# checkout gh-pages
git checkout gh-pages

# delete content
rm *.html
rm *.inv
rm *.js
rm -rf _static


# move content over from the _build dir

cp docs/_build/html _static
cp docs/_build/html/*.html .
cp docs/_build/html/*.inv .
cp docs/_build/html/*.js .

git add .
git commit -m"new build of gh-pages"
git checkout master
