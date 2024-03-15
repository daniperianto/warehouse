package com.ruangmain.warehouse.mapper;

import java.util.List;

public interface Mapper<A, B> {

    B mapTo(A a);

    List<B> mapTo(List<A> a);
}
