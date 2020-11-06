package fr.dla.app.earthquakes.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.cache.event.CacheEntryCreatedListener;
import javax.cache.event.CacheEntryEvent;
import javax.cache.event.CacheEntryExpiredListener;
import javax.cache.event.CacheEntryListenerException;
import javax.cache.event.CacheEntryRemovedListener;
import javax.cache.event.CacheEntryUpdatedListener;

/**
 * A custom cache entry listener.
 */
public class CacheEntryListenerLogger implements CacheEntryCreatedListener<Object, Object>, CacheEntryUpdatedListener<Object, Object>, CacheEntryExpiredListener<Object, Object>, CacheEntryRemovedListener<Object, Object> {

    private static final Logger log = LoggerFactory.getLogger(CacheEntryListenerLogger.class);

    @Override
    public void onCreated(Iterable<CacheEntryEvent<? extends Object, ? extends Object>> events) throws CacheEntryListenerException {
        for (CacheEntryEvent<? extends Object, ? extends Object> entryEvent : events) {
            log.info("Cache event {} for key {} with value {}", entryEvent.getEventType(), entryEvent.getKey(), entryEvent.getValue());
        }
    }

    @Override
    public void onUpdated(Iterable<CacheEntryEvent<? extends Object, ? extends Object>> events) throws CacheEntryListenerException {
        for (CacheEntryEvent<? extends Object, ? extends Object> entryEvent : events) {
            log.info("Cache event {} for key {}, with old value {} and new value {}", entryEvent.getEventType(), entryEvent.getKey(), entryEvent.getOldValue(), entryEvent.getValue());
        }
    }

    @Override
    public void onExpired(Iterable<CacheEntryEvent<? extends Object, ? extends Object>> events) throws CacheEntryListenerException {
        for (CacheEntryEvent<? extends Object, ? extends Object> entryEvent : events) {
            log.info("Cache event {} for key {} with value {}", entryEvent.getEventType(), entryEvent.getKey(), entryEvent.getValue());
        }
    }

    @Override
    public void onRemoved(Iterable<CacheEntryEvent<? extends Object, ? extends Object>> events) throws CacheEntryListenerException {
        for (CacheEntryEvent<? extends Object, ? extends Object> entryEvent : events) {
            log.info("Cache event {} for key {} with value {}", entryEvent.getEventType(), entryEvent.getKey(), entryEvent.getValue());
        }
    }

}
