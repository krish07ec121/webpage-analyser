# webpage-analyser

## Technologies / Libraries

- Spring boot
- Jsoup

## Steps to compile code and build jar file
```
mvn clean install
```

## Steps to start server
```
./mvnw spring-boot:run
```

## Steps to Test Application
Call below endpoint from Postman or browser
```
GET http://localhost:8080//webpage-analyser/report
```
This will return below response(it will take up to 40 seconds to process all hyperlinks up to 4 level). The response contains words respective frequency.
```json
{
    "frequentWords": {
        "and": 18486,
        "to": 16801,
        "EHR": 15802,
        "the": 15629,
        "Services": 13553,
        "of": 11914,
        "Data": 11671,
        "IT": 9763,
        "&": 8363,
        "a": 8359
    },
    "frequentWordPairs": {
        "Cures Act": 4621,
        "Healthcare IT": 4090,
        "EHR Services": 3106,
        "Staff Augmentation": 2733,
        "Muspell CDR": 2538,
        "Services EHR": 2451,
        "Offshore Services": 2434,
        "Big Data": 2420,
        "Data Conversion": 2410,
        "Digital Learning": 2390
    }
}
```


## Explanation
- Spring boot and maven is used for faster development and dependency management.
- Jsoup library is used for parsing HTML pages including hyperlink pages and finding text data.
- Responsibility of each class:
    - **AppController:** Exposes rest endpoint GET /webpage-analyser/report.
    - **WebpageScanner** Scans all the html pages, parse the pages including hyperlinks pages and returns List of text contents.
    - **AppService:** List down all the words and word-pairs from List of content returned from WebpageScanner. Count the frequency of word and word-pair and sort based on count.
    - **Report** Model class to be returned in JSON format.