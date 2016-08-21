package br.com.avaliacao.checkout.mock;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.BooleanConverter;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.beanutils.converters.FloatConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;

public abstract class MockGenerator<T> {

	static {
		// Adicionando os register para caso algum campo seja null.
		ConvertUtils.register(new BigDecimalConverter(null), BigDecimal.class);
		ConvertUtils.register(new LongConverter(null), Long.class);
		ConvertUtils.register(new BooleanConverter(null), Boolean.class);
		ConvertUtils.register(new DoubleConverter(null), Double.class);
		ConvertUtils.register(new IntegerConverter(null), Integer.class);
		ConvertUtils.register(new FloatConverter(null), Float.class);
	}

	public abstract T createMock();

	
	public List<T> createMockList() {
		final List<T> objs = new ArrayList<T>();
		final T mock = createMock();
		if (mock != null) {
			objs.add(mock);
		}
		return objs;
	}

	
	public List<T> createMockList(final int numeroDeOcorrencias) {
		final List<T> objs = new ArrayList<T>(numeroDeOcorrencias);
		for (int i = 0; i < numeroDeOcorrencias; i++) {
			objs.add(createMock());
		}
		return objs;
	}

	public List<T> createMockList(final Map<String, Object> valores) throws MockGeneratorException {
		final List<T> objs = new ArrayList<T>();
		objs.add(createMock(valores));
		return objs;
	}

	
	public List<T> createMockList(final Map<String, Object> valores, final int numeroDeOcorrencias)
			throws MockGeneratorException {
		final List<T> objs = new ArrayList<T>();
		for (int i = 0; i < numeroDeOcorrencias; i++) {
			objs.add(createMock(valores));
		}
		return objs;
	}

	public T createMock(final Map<String, Object> valores) throws MockGeneratorException {

		try {
			final T obj = createMock();
			BeanUtils.populate(obj, valores);
			return obj;
		} catch (final IllegalAccessException e) {
			throw new MockGeneratorException(e.getMessage(), e);
		} catch (final InvocationTargetException e) {
			throw new MockGeneratorException(e.getMessage(), e);
		}

	}

	public T createMockForTrimTest() {
		final T t = createMock();
		modifyMockForTrimTest(t);
		return t;
	}

	public T modifyMockForTrimTest(final T obj) {
		final PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(obj);
		try {
			for (final PropertyDescriptor p : propertyDescriptors) {
				if (String.class.equals(p.getPropertyType())) {
					final String pName = p.getName();
					final String actualValue = BeanUtils.getProperty(obj, pName);
					final StringBuilder newValue = new StringBuilder();
					newValue.append(" ").append(actualValue).append(" ");
					BeanUtils.setProperty(obj, pName, newValue.toString());
				}
			}
		} catch (final IllegalAccessException e) {
			throw new MockGeneratorException(e.getMessage(), e);
		} catch (final InvocationTargetException e) {
			throw new MockGeneratorException(e.getMessage(), e);
		} catch (final NoSuchMethodException e) {
			throw new MockGeneratorException(e.getMessage(), e);
		}
		return obj;
	}

	public abstract void assertMock(T mock);

	public static Calendar createData(final Date datBase, final int qtdDias) {
		final Calendar cal = Calendar.getInstance();
		cal.setTime(datBase);
		cal.add(Calendar.DAY_OF_MONTH, qtdDias);
		return cal;
	}

}
