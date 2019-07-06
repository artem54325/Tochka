package com.example.test;

import com.example.test.controller.ApiController;
import com.example.test.data.SubscriberRepository;
import com.example.test.model.Subscriber;
import com.example.test.service.ServiceHold;
import org.assertj.core.util.Lists;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApplicationTests {
	@Autowired
	private SubscriberRepository repository;

    @Autowired
	private ServiceHold serviceHold;

	@Autowired
	private ApiController controller;

	@After
    public void after(){
        repository.deleteById(UUID.fromString("26c940a1-7228-4ea2-a3bc-e6460b172040"));
        repository.deleteById(UUID.fromString("7badc8f8-65bc-449a-8cde-855234ac63e1"));
        repository.deleteById(UUID.fromString("5597cc3d-c948-48a0-b711-393edf20d9c0"));
        repository.deleteById(UUID.fromString("867f0924-a917-4711-939b-90b179a96392"));
    }

	@Before
	public void setUp(){
		//Delete database
		List<Subscriber> list = new ArrayList<>();

		list.add(new Subscriber(UUID.fromString("26c940a1-7228-4ea2-a3bc-e6460b172040"), "Петров Иван Сергеевич", 1700, 300, true));
		list.add(new Subscriber(UUID.fromString("7badc8f8-65bc-449a-8cde-855234ac63e1"), "Kazitsky Jason", 200, 200, true));
		list.add(new Subscriber(UUID.fromString("5597cc3d-c948-48a0-b711-393edf20d9c0"), "Пархоменко Антон Александрович", 10, 300, true));
		list.add(new Subscriber(UUID.fromString("867f0924-a917-4711-939b-90b179a96392"), "Петечкин Петр Измаилович", 1000000, 1, false));
		//Add database
		repository.saveAll(list);
	}

	@Test
	public void testAddStatus() throws JSONException {//add-status
		JSONObject json = new JSONObject();
		JSONObject addition = new JSONObject();

		addition.put("uuid", "26c940a1-7228-4ea2-a3bc-e6460b172040");
		addition.put("fullName", "Петров Иван Сергеевич");
		addition.put("add", 200);
		addition.put("status", "add");

		json.put("status", "add");
		json.put("result", "");
		json.put("addition", addition);
		json.put("description", new JSONObject());
        json = controller.add(json);
        Assert.assertTrue(json.getBoolean("result"));

        json.put("status","status");
        json.getJSONObject("addition").put("status","status");
        json = controller.status(json);
        Assert.assertEquals(1600, json.getJSONObject("addition").getInt("sum"));
        Assert.assertTrue(json.getJSONObject("addition").getBoolean("isStatus"));

		json = new JSONObject();
		addition = new JSONObject();

		addition.put("uuid", "867f0924-a917-4711-939b-90b179a96392");
		addition.put("fullName", "Петечкин Петр Измаилович");
		addition.put("add", 200);
		addition.put("status", "add");

		json.put("status", "add");
		json.put("result", "");
		json.put("addition", addition);
		json.put("description", new JSONObject());
        json = controller.add(json);
        Assert.assertFalse(json.getBoolean("result"));

        json.put("status","status");
        json.getJSONObject("addition").put("status","status");
        json = controller.status(json);
        Assert.assertEquals(1000000-1, json.getJSONObject("addition").getInt("sum"));
        Assert.assertFalse(json.getJSONObject("addition").getBoolean("isStatus"));

		json = new JSONObject();
		addition = new JSONObject();

		addition.put("uuid", "7badc8f8-65bc-449a-8cde-855234ac63e1");
		addition.put("fullName", "Kazitsky Jason");
		addition.put("add", 0);
		addition.put("status", "add");

		json.put("status", "add");
		json.put("result", "");
		json.put("addition", addition);
		json.put("description", new JSONObject());
        json = controller.add(json);
        Assert.assertTrue(json.getBoolean("result"));

        json.put("status","status");
        json.getJSONObject("addition").put("status","status");
        json = controller.status(json);
        Assert.assertEquals(0, json.getJSONObject("addition").getInt("sum"));
        Assert.assertTrue(json.getJSONObject("addition").getBoolean("isStatus"));
	}

	@Test
	public void testSubstract () throws JSONException {//subscriber-status
        JSONObject json = new JSONObject();
        JSONObject addition = new JSONObject();

        addition.put("uuid", "7badc8f8-65bc-449a-8cde-855234ac63e1");
        addition.put("fullName", "Kazitsky Jason");
        addition.put("substraction", 0);
        addition.put("status", "substract");

        json.put("status", "substract");
        json.put("result", "");
        json.put("addition", addition);
        json.put("description", new JSONObject());
        json = controller.substract(json);
        Assert.assertTrue(json.getBoolean("result"));

        json.put("status","status");
        json.getJSONObject("addition").put("status","status");
        json = controller.status(json);
        Assert.assertEquals(0, json.getJSONObject("addition").getInt("sum"));
        Assert.assertTrue(json.getJSONObject("addition").getBoolean("isStatus"));

        json = new JSONObject();
        addition = new JSONObject();

        addition.put("uuid", "26c940a1-7228-4ea2-a3bc-e6460b172040");
        addition.put("fullName", "Петров Иван Сергеевич");
        addition.put("substraction", 200);
        addition.put("status", "substract");

        json.put("status", "substract");
        json.put("result", "");
        json.put("addition", addition);
        json.put("description", new JSONObject());
        json = controller.substract(json);
        Assert.assertTrue(json.getBoolean("result"));

        json.put("status","status");
        json.getJSONObject("addition").put("status","status");
        json = controller.status(json);
        Assert.assertEquals(1200, json.getJSONObject("addition").getInt("sum"));
        Assert.assertTrue(json.getJSONObject("addition").getBoolean("isStatus"));

        json = new JSONObject();
        addition = new JSONObject();

        addition.put("uuid", "867f0924-a917-4711-939b-90b179a96392");
        addition.put("fullName", "Петечкин Петр Измаилович");
        addition.put("substraction", 200);
        addition.put("status", "substract");

        json.put("status", "substract");
        json.put("result", "");
        json.put("addition", addition);
        json.put("description", new JSONObject());
        json = controller.substract(json);
        Assert.assertFalse(json.getBoolean("result"));

        json.put("status","status");
        json.getJSONObject("addition").put("status","status");
        json = controller.status(json);
        Assert.assertEquals(1000000-1, json.getJSONObject("addition").getInt("sum"));
        Assert.assertFalse(json.getJSONObject("addition").getBoolean("isStatus"));

        json = new JSONObject();
        addition = new JSONObject();

        addition.put("uuid", "5597cc3d-c948-48a0-b711-393edf20d9c0");
        addition.put("fullName", "Kazitsky Jason");
        addition.put("substraction", 15);
        addition.put("status", "substract");

        json.put("status", "substract");
        json.put("result", "");
        json.put("addition", addition);
        json.put("description", new JSONObject());
        json = controller.substract(json);
        Assert.assertFalse(json.getBoolean("result"));

        json.put("status","status");
        json.getJSONObject("addition").put("status","status");
        json = controller.status(json);
        Assert.assertEquals(-290, json.getJSONObject("addition").getInt("sum"));
        Assert.assertTrue(json.getJSONObject("addition").getBoolean("isStatus"));
	}

	@Test
    public void testPing(){
        ResponseEntity entity= controller.ping();
        Assert.assertEquals(entity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testCleaningHold(){
        serviceHold.cleaningHold();
        List<Subscriber> subscribers = Lists.newArrayList(repository.findAll());
        for (int i=0;i<subscribers.size();i++){
            Assert.assertEquals(subscribers.get(i).getHold(), 0);
        }
        Assert.assertEquals(repository.findById(UUID.fromString("26c940a1-7228-4ea2-a3bc-e6460b172040")).get().getBalance(), 1400);
        Assert.assertEquals(repository.findById(UUID.fromString("7badc8f8-65bc-449a-8cde-855234ac63e1")).get().getBalance(), 0);
        Assert.assertEquals(repository.findById(UUID.fromString("5597cc3d-c948-48a0-b711-393edf20d9c0")).get().getBalance(), -290);
        Assert.assertEquals(repository.findById(UUID.fromString("867f0924-a917-4711-939b-90b179a96392")).get().getBalance(), 1000000-1);
    }

}
