package extrace.sorter.work;

import java.util.List;

import extrace.model.ExpressEntity;

/**
 * Created by 黎明 on 2016/5/7.
 */
public interface SearchWorkFragmentView {
    void onError(String errorMessage);
    void onSuccess(List<ExpressEntity> list);
}
