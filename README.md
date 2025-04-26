# URL SHORTENING API

A simple url shortening api

## FEATURES

- Shorten long URLS
- Redirect short URLS to original urls
- Retrieve original URLS from short URLS
- Track click analytics(number of clicks, clicks by bots)
- View statistics like top-performing URLS
- Safety features(prevents shortening short urls)

## TECHNOLOGIES USED
- Java 23
- Spring boot
- MySQL 
- Maven
- Lombok
- Hibernate

## GETTING STARTED

### PRE-REQUISITES
- Java 17+
- Maven
- MYSQL

### RUNNING LOCALLY
1. Clone the repository: https://github.com/kusoroadeolu/urlshortener
2. Navigate into the project folder: "cd urlshortener"
3. Set up your database 
4. Configure your application properties to match your database
5. Run the project: ./mvnw spring-boot:run


## API ENDPOINTS
### URL BASED ENDPOINTS

| Method | Endpoint                   | Description                             |
|--------|----------------------------|-----------------------------------------|
| POST   | `/api/shorten`             | Shorten a long URL                      |
| GET    | `/{shortCode}`             | Redirect to the original URL            |
| GET    | `/api/original/{shortUrl}` | Get the original URL from a short URL   |
| DELETE | `/api/delete`              | Delete a short URL                      |
| PUT    | `/api/update`              | Update the short URL of an existing URL |

### ANALYTICS BASED ENDPOINTS

| HTTP Method | Endpoint                         | Description                                      | Parameters |
|:------------|:----------------------------------|:-------------------------------------------------|:-----------|
| GET         | `/api/click/all`                  | Get all recorded clicks                          | None        |
| GET         | `/api/click/by-url`               | Get clicks for a specific short URL              | `shortUrl` (query param) |
| GET         | `/api/click/count`                | Get total number of clicks for a short URL       | `shortUrl` (query param) |
| GET         | `/api/click/date-count`           | Get daily click counts for a short URL           | `shortUrl` (query param) |
| GET         | `/api/click/bots`                 | Get all bot clicks                               | None        |
| GET         | `/api/click/bots/by-url`          | Get bot clicks for a specific short URL          | `shortUrl` (query param) |
| GET         | `/api/click/humans`               | Get all human clicks                             | None        |
| GET         | `/api/click/humans/by-url`        | Get human clicks for a specific short URL        | `shortUrl` (query param) |
| GET         | `/api/click/country`              | Get clicks by country                            | `country` (query param) |
| GET         | `/api/click/device`               | Get clicks by device type                        | `device` (query param) |
| GET         | `/api/click/browser`              | Get clicks by browser type                       | `browser` (query param) |
| GET         | `/api/click/top-performing`       | Get top-performing short URLs                    | `limit` (query param) |
| GET         | `/api/click/hour`                 | Get hourly click stats for a specific date       | `targetDate` (query param) |
| GET         | `/api/click/referrer`             | Get click counts grouped by referrer             | None        |
| GET         | `/api/click/referrer/by-date`     | Get referrer click counts for a specific date    | `targetDate` (query param) |


## License
This project is open-source and available under the [MIT License](LICENSE).

## PROJECT RECOMMENDED BY: 
https://roadmap.sh/projects/url-shortening-service