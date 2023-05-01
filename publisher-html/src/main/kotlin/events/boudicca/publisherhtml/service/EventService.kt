package events.boudicca.publisherhtml.service

import events.boudicca.api.Event
import events.boudicca.api.util.EventMapper
import events.boudicca.openapi.ApiClient
import events.boudicca.openapi.api.EventPublisherResourceApi
import events.boudicca.openapi.model.SearchDTO
import org.springframework.stereotype.Service

@Service
class EventService {
    private val publisherApi: EventPublisherResourceApi

    init {
        val apiClient = ApiClient()
        apiClient.updateBaseUri("https://api.boudicca.events") //TODO make configurable
        publisherApi = EventPublisherResourceApi(apiClient)
    }

    fun getAllEvents(): Set<Event> {
        return publisherApi.eventsGet().map{EventMapper.toEvent(it)}.toSet()
    }

    fun search(searchDTO: SearchDTO): Set<Event> {
        return publisherApi.eventsSearchPost(searchDTO).map{EventMapper.toEvent(it)}.toSet()
    }
}