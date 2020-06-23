package com.github.ricardoebbers.octocatabsoluteunit.domain.service.impl

import com.github.ricardoebbers.octocatabsoluteunit.domain.entity.File
import com.github.ricardoebbers.octocatabsoluteunit.domain.entity.Folder
import com.github.ricardoebbers.octocatabsoluteunit.domain.entity.Report
import com.github.ricardoebbers.octocatabsoluteunit.domain.entity.Scrappable
import com.github.ricardoebbers.octocatabsoluteunit.domain.repository.ReportRepository
import com.github.ricardoebbers.octocatabsoluteunit.domain.service.ScrapingService
import com.github.ricardoebbers.octocatabsoluteunit.rest.client.RequestClient
import org.springframework.stereotype.Service
import java.net.URL
import java.util.concurrent.CompletableFuture
import java.util.logging.Logger

@Service
class ScrapingServiceImpl(
        val requestClient: RequestClient,
        val repository: ReportRepository
) : ScrapingService {

    private val log: Logger = Logger.getLogger(ScrapingService::class.simpleName)

    override fun fetchOrScrape(baseUri: URL): Report {
        log.info("M=fetchOrScrape, I=init, uri=$baseUri")
        return try {
            val root = Folder(baseUri.path)
            repository.findOneByUri(baseUri.path).orElseGet {
                scrapeAndSave(baseUri, root)
            }
        } catch (ex: Throwable) {
            log.warning("M=fetchOrScrape, E=${ex.message}, $ex")
            throw ex
        } finally {
            log.info("M=fetchOrScrape, I=finish, uri=$baseUri")
        }
    }

    private fun scrapeAndSave(baseUri: URL, root: Folder): Report {
        log.info("M=scrapeAndSave, I=init, uri=$baseUri")
        val report = Report(baseUri.path)
        buildTree(root, report)
        return repository.save(report).also {
            log.info("M=scrapeAndSave, I=saved_report, report=$it")
        }
    }

    private fun buildTree(root: Scrappable, report: Report): Scrappable {
        log.info("M=buildTree, I=init, uri=${root.uri}")
        // get root html by requesting it via http
        root.visit(fetchData(root).get())
        // build list of children based on its uri (folder uri contains 'tree', files contains 'blob')
        when (root) {
            is Folder -> buildChildren(root, report)
            is File -> calculate(root, report)
        }
        return root
    }

    private fun buildChildren(root: Folder, report: Report) {
        log.info("M=buildChildren, I=init, uri=${root.uri}")
        root.extractChildrenFromData()
        // until there are no children, run prepareTree recursively
        root.children.forEach {
            if (it.isVisited.not()) buildTree(it, report)
        }
    }

    private fun calculate(root: File, report: Report) {
        log.info("M=calculate, I=init, uri=${root.uri}")
        root.calculateMeasurements()
        report.addMeasurements(root.measurement)
    }

    private fun fetchData(root: Scrappable): CompletableFuture<String> {
        log.info("M=fetchData, I=init, uri=${root.uri}")
        // send a get request for the http
        return requestClient.get(root.uri)
    }
}
