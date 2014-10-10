echo 'Building Local Dependencies'
mkdir local_dependencies
cd local_dependencies
git clone https://github.com/apache/pdfbox.git
cd pdfbox
mvn clean install -DskipTests
cd ..
git clone https://github.com/DImuthuUpe/Tesseract-API.git
cd Tesseract-API
mvn clean install
