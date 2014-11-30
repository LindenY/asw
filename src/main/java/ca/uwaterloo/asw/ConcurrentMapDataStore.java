package ca.uwaterloo.asw;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uwaterloo.asw.reflection.TypeToken;

public class ConcurrentMapDataStore implements DataStore {

	private ConcurrentHashMap<TypeToken<?>, List<Object>> concurrentMap;

	public ConcurrentMapDataStore() {
		concurrentMap = new ConcurrentHashMap<TypeToken<?>, List<Object>>();
	}

	public void add(Object obj) {
		add(obj, null);
	}

	public void add(Object obj, String name) {
		TypeToken<?> typeToken = TypeToken.get(obj.getClass(), name);
		List<Object> objs = concurrentMap.get(typeToken);

		if (objs == null) {
			objs = new ArrayList<Object>();
			concurrentMap.put(typeToken, objs);
		}

		synchronized (objs) {
			objs.add(obj);
		}
	}

	public void addAll(List<Object> objs) {
		for (Object obj : objs) {
			add(obj);
		}
	}

	/**
	 * NOTE: This method is not encouraged to be used, because right now it uses
	 * normal {@link ArrayList} to store the objects and {@link ArrayList} does
	 * not support concurrent operation. To prevent
	 * {@link ConcurrentModificationException} from happening,it uses
	 * 'synchronized' block to lock the entire {@link ArrayList} from accessing.
	 * By using this method, it surely prevent the happening of the
	 * {@link ConcurrentModificationException}, but with the cost of
	 * performance.
	 */
	@Deprecated
	public boolean contain(Object obj) {

		TypeToken<?> typeToken = TypeToken.get(obj.getClass());

		if (!contain(typeToken)) {
			return false;
		}

		List<Object> objs = concurrentMap.get(typeToken);
		synchronized (objs) {
			return objs.contains(obj);
		}
	}

	public boolean contain(Class<?> type) {
		return contain(TypeToken.get(type));
	}

	public boolean contain(Class<?> type, String name) {
		return contain(TypeToken.get(type, name));
	}

	public boolean contain(TypeToken<?> typeToken) {

		List<Object> objs = concurrentMap.get(typeToken);

		if (objs == null) {
			return false;
		}

		return objs.size() > 0 ? true : false;
	}

	public boolean containAll(List<TypeToken<?>> typeTokens) {
		for (TypeToken<?> typeToken : typeTokens) {
			if (!contain(typeToken)) {
				return false;
			}
		}
		return true;
	}

	public <T> T getAndRemove(Class<T> type) {
		return getAndRemove(TypeToken.get(type));
	}

	public <T> T getAndRemove(Class<T> type, String name) {
		return getAndRemove(TypeToken.get(type, name));
	}

	@SuppressWarnings("unchecked")
	public <T> T getAndRemove(TypeToken<T> typeToken) {

		List<Object> objs = concurrentMap.get(typeToken);

		if (objs == null || objs.size() <= 0) {
			return null;
		}

		Object obj = null;
		synchronized (objs) {
			obj = objs.get(0);
			objs.remove(0);
		}

		return (T) obj;
	}


	public DataNode getAndRemoveAll(List<TypeToken<?>> typeTokens) {

		DataNode dataNode = new DataNode();

		for (TypeToken<?> typeToken : typeTokens) {
			dataNode.put(getAndRemove(typeToken));
		}

		return dataNode;
	}

}