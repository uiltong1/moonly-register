package br.com.moonlyRegister.mapper.serialization.converter;

import java.util.ArrayList;
import java.util.List;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

public class DozerConverter {

	private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();

	public static <Origin, Destination> Destination parseObject(Origin origin, Class<Destination> destination) {
		return mapper.map(origin, destination);
	}

	public static <Origin, Destination> List<Destination> parseListObjects(List<Origin> origins,
			Class<Destination> destination) {
		List<Destination> destinationListObjects = new ArrayList<Destination>();
		for (Origin origin : origins) {
			destinationListObjects.add(mapper.map(origin, destination));
		}
		return destinationListObjects;
	}

}
