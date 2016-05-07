package extrace.sorter.open.ep_search.express_list;


import java.util.List;

import extrace.model.ExpressInfo;


/**
 * Created by 黎明 on 2016/4/26.
 */
public interface ExpressListPresenter
{
    void onSearchEByPackageID(String packageID);
    void onSuccess(List<ExpressInfo> list);
    void onExpressListFail(String errorMessage);
}
