package com.ig.igtradinggame.network;

import com.ig.igtradinggame.TestUtils;
import com.ig.igtradinggame.models.ClientModel;
import com.ig.igtradinggame.models.MarketModel;
import com.ig.igtradinggame.models.OpenPositionIdResponse;
import com.ig.igtradinggame.models.OpenPositionModel;
import com.ig.igtradinggame.features.maingame.trade.buy.OpenPositionRequest;
import com.ig.igtradinggame.network.retrofit_impl.IGAPIService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class IGAPIServiceTest {
    private static final String BASE_URL = "http://0.0.0.0:8085/"; // runs using localhost
    private static final int UPDATE_FREQUENCY_MILLIS = 500;

    private IGAPIServiceInterface api;
    private String clientID = "client_1509483146163";

    @Before
    public void setUp() throws Exception {
        api = new IGAPIService(BASE_URL);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void createClient() throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);

        api.createClient(TestUtils.randomAlphaNumeric(20), new IGAPIService.OnClientLoadedListener() {
            @Override
            public void onComplete(ClientModel response) {
                latch.countDown();
            }

            @Override
            public void onError(String errorMessage) {
                fail(errorMessage);
            }
        });

        boolean completed = latch.await(10, TimeUnit.SECONDS);
        assertTrue(completed);
    }

    @Test
    public void getClientInfo() throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);

        api.getClientInfo(createTestClient(), new IGAPIService.OnClientLoadedListener() {
            @Override
            public void onComplete(ClientModel response) {
                latch.countDown();
            }

            @Override
            public void onError(String errorMessage) {
                fail(errorMessage);
            }
        });

        boolean completed = latch.await(10, TimeUnit.SECONDS);
        assertTrue(completed);
    }

    @Test
    public void getClientInfoStreaming() throws Exception {

    }

    @Test
    public void getAllMarketsSucceeds() throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);

        api.getAllMarkets(new IGAPIService.OnMarketsLoadedCompleteListener() {
            @Override
            public void onComplete(List<MarketModel> marketList) {
                latch.countDown();
            }

            @Override
            public void onError(String errorMessage) {
                fail(errorMessage);
            }
        });

        boolean completed = latch.await(10, TimeUnit.SECONDS);
        assertTrue(completed);
    }

    @Test
    public void getAllMarketsStreaming() throws Exception {
        final CountDownLatch latch = new CountDownLatch(10);
        api.getAllMarketsStreaming(UPDATE_FREQUENCY_MILLIS)
                .subscribe(new Observer<List<MarketModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<MarketModel> marketModels) {
                        latch.countDown();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        fail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        boolean completed = latch.await(10, TimeUnit.SECONDS);
        assertTrue(completed);
    }

    @Test
    public void getOpenPositions() throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);

        api.getOpenPositions(createTestClient(), new IGAPIService.OnOpenPositionsLoadedCompleteListener() {
            @Override
            public void onComplete(List<OpenPositionModel> openPositionModels) {
                latch.countDown();
            }

            @Override
            public void onError(String errorMessage) {
                fail(errorMessage);
            }
        });

        boolean completed = latch.await(10, TimeUnit.SECONDS);
        assertTrue(completed);
    }

    @Test
    public void getOpenPositionsStreaming() throws Exception {

    }

    @Test
    public void openPosition() throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);

        OpenPositionRequest request = new OpenPositionRequest("market_1", 1);

        api.openPosition(createTestClient(), request, new IGAPIService.OnOpenPositionCompleteListener() {
            @Override
            public void onComplete(OpenPositionIdResponse openPositionIdResponse) {
                latch.countDown();
            }

            @Override
            public void onError(String errorMessage) {
                fail(errorMessage);
            }
        });

        boolean completed = latch.await(10, TimeUnit.SECONDS);
        assertTrue(completed);
    }

    @Test
    public void closePosition() throws Exception {
        final String clientID = createTestClient();
        final String opID = createTestPosition(clientID);

        final CountDownLatch latch = new CountDownLatch(1);

        api.closePosition(clientID, opID, new IGAPIService.OnClosePositionCompleteListener() {
            @Override
            public void onComplete() {
                latch.countDown();
            }

            @Override
            public void onError(String errorMessage) {
                fail(errorMessage);
            }
        });

        boolean completed = latch.await(10, TimeUnit.SECONDS);
        assertTrue(completed);
    }

    /**
     * @return the ID of the newly created client
     */
    private String createTestClient() {
        final CountDownLatch latch = new CountDownLatch(1);
        final StringBuilder stringBuilder = new StringBuilder();

        final String username = "test_user_" + TestUtils.randomAlphaNumeric(20);

        api.createClient(username, new IGAPIService.OnClientLoadedListener() {
            @Override
            public void onComplete(ClientModel response) {
                System.out.println("CREATED CLIENT: " + response.getUserName());
                stringBuilder.append(response.getId());
                latch.countDown();
            }

            @Override
            public void onError(String errorMessage) {
                fail(errorMessage);
            }
        });

        try {
            latch.await(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }

    /**
     * @param clientId
     * @return The ID of the newly created position
     */
    private String createTestPosition(String clientId) {
        final CountDownLatch latch = new CountDownLatch(1);
        final StringBuilder stringBuilder = new StringBuilder();
        api.openPosition(clientId, new OpenPositionRequest("market_1", 1), new IGAPIService.OnOpenPositionCompleteListener() {
            @Override
            public void onComplete(OpenPositionIdResponse openPositionIdResponse) {
                stringBuilder.append(openPositionIdResponse.getOpenPositionId());
                latch.countDown();
            }

            @Override
            public void onError(String errorMessage) {
                fail(errorMessage);
            }
        });

        try {
            latch.await(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }
}