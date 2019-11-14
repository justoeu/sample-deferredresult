package br.com.justoeu.example.gateway.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import static br.com.justoeu.example.gateway.http.URLMapping.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(ROOT_API)
@Slf4j
public class PingHealthCheck {

    private final static String PONG = "{\"result\": \"pong\"}";

    @Autowired
    private ThreadPoolTaskExecutor executor;


    @GetMapping(value = API_PING_HEALTH_CHECK, produces = APPLICATION_JSON_VALUE+";charset=UTF-8")
    public ResponseEntity<String> ping() {

        return new ResponseEntity("Lindo do papai", HttpStatus.OK);
    }

    @GetMapping(value = API_CHECK_HEALTH_CHECK, produces = APPLICATION_JSON_VALUE+";charset=UTF-8")
    public DeferredResult<ResponseEntity<?>> check() {
        DeferredResult<ResponseEntity<?>> deferredResult = new DeferredResult<>();
        try {

            ListenableFuture<String> task = executor.submitListenable(() -> {
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e){ }
                return PONG;
            });

            task.addCallback(
                    new ListenableFutureCallback<>() {
                        @Override
                        public void onFailure(final Throwable e) {
                            log.error(e.getMessage(),e);
                            deferredResult.setResult(new ResponseEntity("{result: 'Thread not Response :('}", HttpStatus.SERVICE_UNAVAILABLE));
                        }

                        @Override
                        public void onSuccess(final String result) {
                            log.info("Machine is OK!!! Uhull :)");
                            deferredResult.setResult(new ResponseEntity(result, HttpStatus.OK));
                        }
                    }
            );

        } catch (RuntimeException e) {
            log.error(e.getMessage(), e);

            deferredResult.setResult(new ResponseEntity("{result: 'Ops Daisy, Sorry!!'}", HttpStatus.INTERNAL_SERVER_ERROR));
        }

        return deferredResult;
    }

}