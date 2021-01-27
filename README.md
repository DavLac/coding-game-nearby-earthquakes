# Nearby Earthquakes

This application was generated using JHipster 6.10.1, you can find documentation and help at [https://www.jhipster.tech/documentation-archive/v6.10.1](https://www.jhipster.tech/documentation-archive/v6.10.1).

This is a "microservice" application intended to be part of a microservice architecture, please refer to the [Doing microservices with JHipster][] page of the documentation for more information.

## Readme links

- [Technical task instructions](README-TASK.md)
- [How to run the app](RUN.md)

## Get Nearby earthquakes endpoint

List 10 earthquakes that happened in the closest proximity to input point, in the order from the closest to the furthest. Input point = latitude + longitude.

- **Http method** : GET
- **URI** : /api/earthquakes/nearby
- **DEV profile url example** : http://localhost:PORT_NUMBER/api/earthquakes/nearby?latitude=40.730610&longitude=-73.935242

### Parameters

#### Input

| Parameter name | Type          | Field type | Constraints                                                      | Example    |
| -------------- | ------------- | ---------- | ---------------------------------------------------------------- | ---------- |
| latitude       | @RequestParam | Float      | @NotNull @Digits(integer = 2, fraction = 6) @Max(90) @Min(-90)   | 40.730610  |
| longitude      | @RequestParam | Float      | @NotNull @Digits(integer = 3, fraction = 6) @Max(180) @Min(-180) | -73.935242 |

Example :

```
40.730610
-73.935242
```

#### Output

| Field type     |
| -------------- |
| List of String |

Example :

```
M 1.3 - 2km SSE of Contoocook, New Hampshire || 331
M 1.3 - 2km ENE of Belmont, Virginia || 354
M 2.4 - 83km ESE of Nantucket, Massachusetts || 406
M 1.3 - 13km ENE of Barre, Vermont || 410
M 0.7 - 18km NW of Norfolk, New York || 476
M 2.0 - 17km NW of Norfolk, New York || 476
M 1.7 - 19km NNW of Beaupre, Canada || 758
M 1.9 - 13km SW of La Malbaie, Canada || 814
M 2.4 - 16km N of Lenoir, North Carolina || 840
M 2.4 - 12km ESE of Carlisle, Kentucky || 896
```

### Endpoint dependencies

| Client name      | Endpoint description                                  | Url                                                                         |
| ---------------- | ----------------------------------------------------- | --------------------------------------------------------------------------- |
| USGS Earthquakes | Get all earthquakes that happened during last 30 days | https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_month.geojson |

## Caching strategy

All Earthquakes are updated every minutes (Source : Usgs website). So, it's not necessary to get earthquakes list at each call.
A **EhCache** cache with **1 minute time to live** is configured when calling all earthquakes.

Cache configuration files :

- application-dev.yml
- application-prod.yml

To change the cache TTL, change this property :
`jhipster.cache.ehcache.time-to-live-seconds`

To disable the cache, add this property :
`spring.cache.type: none`

[doing microservices with jhipster]: https://www.jhipster.tech/documentation-archive/v6.10.1/microservices-architecture/
