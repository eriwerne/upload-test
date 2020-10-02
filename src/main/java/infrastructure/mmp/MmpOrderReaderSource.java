package infrastructure.mmp;

import application.reading.exception.OrderNotFound;
import application.reading.exception.ResourceFailure;
import application.reading.orderreader.OrderReaderSource;
import core.image.ImageOrder;
import infrastructure.mmp.mapping.MmpImageOrderMapper;
import infrastructure.mmp.schema.MmpOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MmpOrderReaderSource implements OrderReaderSource {
    private final MmpApi mmpApi;
    private final MmpImageOrderMapper mapper;

    public MmpOrderReaderSource(MmpApi mmpApi) {
        this.mmpApi = mmpApi;
        mapper = new MmpImageOrderMapper();
    }

    @Override
    public ArrayList<String> readArticleNumbersInOrder(String orderNumber) throws OrderNotFound, ResourceFailure {
        String json = mmpApi.getImageOrdersForOrderNumber(orderNumber);
        if(json.equals("[]\n"))
            throw new OrderNotFound(orderNumber);
        MmpOrder mmpOrder = mapper.mapJsonToMmpObject(json);
        HashMap<String, List<ImageOrder>> articleImageOrders = mapper.mapFromMmpObject(mmpOrder);
        return new ArrayList<>(articleImageOrders.keySet());
    }
}
