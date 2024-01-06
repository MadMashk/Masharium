package mash.masharium.service;


import mash.masharium.entity.ComponentWritingOffOperation;
import mash.masharium.entity.WritingOffOperation;

import java.util.UUID;

public interface WritingOffOperationService {

    ComponentWritingOffOperation createComponentOfWritingOfOperation(ComponentWritingOffOperation componentWritingOffOperation);

    WritingOffOperation createWritingOffOperation(UUID orderId);


}
