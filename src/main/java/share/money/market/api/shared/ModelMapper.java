package share.money.market.api.shared;

import org.modelmapper.convention.MatchingStrategies;

public class ModelMapper {
    private static org.modelmapper.ModelMapper mapper;

    static {
        mapper = new org.modelmapper.ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    private ModelMapper() {

    }

    public static <T> T map(Object from, Class<T> to) {
        return mapper.map(from, to);
    }
}
