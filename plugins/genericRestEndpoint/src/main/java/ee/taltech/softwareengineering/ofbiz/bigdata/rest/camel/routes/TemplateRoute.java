package ee.taltech.softwareengineering.ofbiz.bigdata.rest.camel.routes;

import ee.taltech.softwareengineering.ofbiz.bigdata.rest.camel.service.TemplateService;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.service.LocalDispatcher;

public class TemplateRoute extends BaseRoute {

	private TemplateService templateService;

	// localdispatcher is fed into the service by ofbiz, it has some info the plugin can use, like delegator
	// it can also be used to call other ofbiz services by string
	public TemplateRoute(LocalDispatcher localDispatcher) throws GenericEntityException {
		super(localDispatcher);
		templateService = new TemplateService(localDispatcher.getDelegator());
	}

	@Override
	public void configure() {
		// makes all of it accessible on localhost:4567
		restConfiguration("rest-api")
				.component("restlet")
				.host("localhost")
				.port("4567")
				.bindingMode(RestBindingMode.auto);

		// enables GET on /api/{something}, on that URL GET, the "getInvoices" method is called out on templateService
		rest("/api/v1/entity")
				.get("/{entity}")
				.id("get-all-entity")
				.produces("application/json")
				.route()
				.bean(templateService, "getAll")
				.endRest();

		rest("/api/v1/entity")
				.post("/{entity}")
				.id("insert-entity")
				.produces("application/json")
				.route()
				.bean(templateService, "insert")
				.endRest();

		// enables POST on /api/invoice, could write POJOs for entities if needed for some reason
		// just convert it to genericvalue
//		rest("/api")
//				.post("/invoices")
////                .type(InvoicePojo.class)
//				.id("invoice-post")
//				.produces("application/json")
//				.route()
//				.bean(templateService, "createInvoice")
//				.endRest();
	}
}
