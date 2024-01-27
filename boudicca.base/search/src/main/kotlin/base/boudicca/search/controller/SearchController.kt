package base.boudicca.search.controller

import base.boudicca.api.search.SearchApi
import base.boudicca.api.search.model.*
import base.boudicca.search.BoudiccaSearchProperties
import base.boudicca.search.service.QueryService
import base.boudicca.search.service.FilersService
import base.boudicca.search.service.SynchronizationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
class SearchController @Autowired constructor(
    private val filersService: FilersService,
    private val queryService: QueryService,
    private val synchronizationService: SynchronizationService,
    private val boudiccaSearchProperties: BoudiccaSearchProperties
) : SearchApi {

    @PostMapping(
        "filtersFor",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    @ResponseBody
    override fun filtersFor(@RequestBody filterQueryDTO: FilterQueryDTO): FilterResultDTO {
        if (boudiccaSearchProperties.localMode) {
            synchronizationService.update()
        }
        return filersService.filtersFor(filterQueryDTO)
    }

    @PostMapping(
        "queryEntries",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    @ResponseBody
    override fun queryEntries(@RequestBody queryDTO: QueryDTO): ResultDTO {
        if (boudiccaSearchProperties.localMode) {
            synchronizationService.update()
        }
        return queryService.query(queryDTO)
    }
}
