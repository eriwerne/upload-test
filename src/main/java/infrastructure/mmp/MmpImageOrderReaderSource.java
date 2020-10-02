package infrastructure.mmp;

import application.reading.exception.OrderNotFound;
import application.reading.exception.ResourceFailure;
import application.reading.imageorderreader.ImageOrderReaderSource;
import core.image.ImageOrder;
import infrastructure.mmp.mapping.MmpImageOrderMapper;
import infrastructure.mmp.schema.MmpOrder;

import java.util.HashMap;
import java.util.List;

public class MmpImageOrderReaderSource implements ImageOrderReaderSource {
    private final MmpApi mmpApi;
    private final MmpImageOrderMapper mapper;

    public MmpImageOrderReaderSource(MmpApi mmpApi) {
        this.mmpApi = mmpApi;
        mapper = new MmpImageOrderMapper();
    }

    @Override
    public HashMap<String, List<ImageOrder>> readImageOrdersForOrderNumber(String orderNumber) throws OrderNotFound, ResourceFailure {
        String json = mmpApi.getImageOrdersForOrderNumber(orderNumber);
        if(json.equals("[]\n"))
            throw new OrderNotFound(orderNumber);
        MmpOrder mmpOrder = mapper.mapJsonToMmpObject(json);
        return mapper.mapFromMmpObject(mmpOrder);
    }

}
