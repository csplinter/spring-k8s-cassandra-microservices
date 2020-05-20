package com.datastax.sample.resources;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.datastax.oss.driver.api.core.DriverException;
import com.datastax.sample.entity.TickData;
import com.datastax.sample.repository.TickDataRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

/**
 * REST Resources working with {@link TickData}.
 * This CRUD resource leverages on standard HTTP Codes and patterns.
 * 
 */
@RestController
@Api(value = "/api/v1/tickdata", description = "TickData Services Rest Resource")
@RequestMapping("/api/v1/tickdata")
public class TickDataResource {

    /** Logger for the class. */
    private static final Logger logger = LoggerFactory.getLogger(TickDataResource.class);
    
    /** Service implementation Injection. */
    private TickDataRepository tickRepository;

    /**
     * Best practice : Inversion of Control through constructor and no More @Inject nor @Autowired
     * 
     * @param tickRepo
     *      repository implementation
     */
    public TickDataResource(TickDataRepository tickRepo) {
        this.tickRepository = tickRepo;
    }
    
    /**
     * List all tickdata in DB. Please not there is no implementation of paging. 
     * As such result can be really large. If you query tables with large number 
     * of rows, please use Paging.
     *  
     * @return
     *      list all {@link TickData} available in the table 
     */
    @RequestMapping(
            method = GET,
            value = "/",
            produces = APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "List all TickData available in the table", response = List.class)
    @ApiResponse(
            code = 200,
            message = "List all tick available in the table")
    public ResponseEntity<List<TickData>> findAll() {
        logger.debug("Retrieving all TickData");
        // Returning an empty list is better than 204 code (meaning no valued expected)
        List <TickData > tt= new ArrayList<>();
       
        for (TickData tick : tickRepository.findAll()) {
            tt.add(tick);
        }
        return ResponseEntity.ok(tt);
    }
    
    /**
     * Retrieve TickData list for a symbol
     *
     * @param symbol
     *      unique symbol
     * @return
     *      list of tickData
     */
    @RequestMapping(
            value = "/{symbol}",
            method = GET,
            produces = APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "List all TickData available in the table for this symbol", response = List.class)
    @ApiResponse(
            code = 200,
            message = "List all tick available in the table for this symbol")
    public ResponseEntity<List<TickData>>  findBySymbol(
            @ApiParam(name="symbol", 
                     value="symbol for a tickdata",
                     example = "TSLA",
                     required=true )
            @PathVariable(value = "symbol") String symbol) {
        logger.debug("Retrieving TickData with symbol {}", symbol);
        return ResponseEntity.ok(tickRepository.findByTickDataKeySymbol(symbol));
    }
    
    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public String _errorBadRequestHandler(IllegalArgumentException ex) {
        return "Invalid Parameter: " + ex.getMessage();
    }
    
    /**
     * Converts {@link DriverException}s into HTTP 500 error codes and outputs the error message as
     * the response body.
     *
     * @param e The {@link DriverException}.
     * @return The error message to be used as response body.
     */
    @ExceptionHandler(DriverException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String _errorDriverHandler(DriverException e) {
      return e.getMessage();
    }

}
