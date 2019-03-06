import static org.junit.Assert.*;

import org.junit.Test;

import st.Parser;

import org.junit.Before;
import static org.junit.Assert.assertEquals;

public class Task1_Functional {
	private Parser parser;
	
	@Before
	public void set_up() {
		parser = new Parser();
	}
	
	//Test options with the same name
	//Most recent option should override previous option
	@Test
	public void same_name_existing_option_test() {
		parser.add("output", "o", Parser.STRING);
		parser.add("output", "out", Parser.STRING);
		
		//Check first same name option
		parser.parse("--output=output.txt");
		assertNotEquals(parser.getString("o"), "output.txt");
		
		//Check second same name option which should override
		parser.parse("--output=output.txt");
		assertEquals(parser.getString("out"), "output.txt");
		
	}
	
	@Test
	public void same_name_different_type_test() {
		parser.add("output", "o", Parser.STRING);
		parser.add("output","out", Parser.INTEGER);
		
		parser.parse("--output=output.txt");
		assertNotEquals(parser.getString("o"), "output.txt");
		
		parser.parse("--output=5");
		assertEquals(parser.getInteger("out"), 5);
		
		
		
		parser.add("output", "o", Parser.BOOLEAN);
		parser.parse("--output=5");
		assertNotEquals(parser.getInteger("out"), 5);
		
		parser.parse("--output=true");
		assertEquals(parser.getBoolean("o"), true);
		
		
		
		parser.add("output",  "out", Parser.CHAR);
		parser.parse("--output=true");
		assertNotEquals(parser.getBoolean("o"), true);
		
		parser.parse("--output=a");
		assertEquals(parser.getChar("out"), 'a');
		
		
	}
	
	@Test
	public void case_sensitive_test() {
		parser.add("Output", "O", Parser.STRING);
		parser.add("output", "o", Parser.STRING);
		
		parser.parse("--Output=Output.txt");
		assertEquals(parser.getString("O"), "Output.txt");
		
		parser.parse("--output=output.txt");
		assertEquals(parser.getString("o"), "output.txt");
	}
	
	@Test
	public void case_sensitive_different_types_test() {
		parser.add("Output", "O", Parser.STRING);
		parser.add("output","o", Parser.INTEGER);
		parser.add("oUtput", "o", Parser.BOOLEAN);
		parser.add("ouTput","O", Parser.CHAR);
		
		
		parser.parse("--Output=Output.txt");
		assertEquals(parser.getString("Output"), "Output.txt");
		
		parser.parse("--output=6");
		assertEquals(parser.getInteger("output"), 6);
		
		parser.parse("--oUtput=true");
		assertEquals(parser.getBoolean("oUtput"), true);
		
		parser.parse("--ouTput=a");
		assertEquals(parser.getChar("ouTput"), 'a');
		
	}
	
	@Test
	public void same_name_as_shortcut_of_different_option_test() {
		parser.add("output", "o", Parser.STRING);
		parser.add("o", "shortcut", Parser.STRING);
		
		parser.parse("-o=shortcut");
		assertEquals(parser.getString("output"), "shortcut");
		
		parser.parse("--o=name");
		assertEquals(parser.getString("o"), "name");
	}
	
	@Test
	public void same_name_as_shortcut_of_different_option_differnt_type_test() {
		parser.add("output", "o", Parser.STRING);
		parser.add("o", "shortcut", Parser.INTEGER);
		
		parser.parse("-o=shortcut");
		assertEquals(parser.getString("output"), "shortcut");
		
		parser.parse("--o=7");
		assertEquals(parser.getInteger("o"), 7);
	}
	
	
	@Test
	public void boolean_false_case_insensitive_test() {
		parser.add("output", "shortcut", Parser.BOOLEAN);
		parser.add("output2", "shortcut2", Parser.BOOLEAN);
		
		parser.parse("--output=false");
		assertEquals(parser.getBoolean("output"), false);
		
		parser.parse("--output2=False");
		assertEquals(parser.getBoolean("output2"), false);
		
	}
	
	@Test
	public void boolean_set_to_false_using_0_test() {
		parser.add("output", "shortcut", Parser.BOOLEAN);
		parser.parse("--output=0");
		assertEquals(parser.getBoolean("output"), false);
		
	}
	
	@Test
	public void boolean_set_to_false_when_option_not_used_test() {
		parser.add("output", "shortcut", Parser.BOOLEAN);
		assertEquals(parser.getBoolean("output"), false);
	}
	
	public void boolean_set_to_true_when_used_for_anything_else_test() {
		parser.add("output", "O", Parser.BOOLEAN);
		parser.parse("-O");
		assertEquals(parser.getBoolean("output"), true);
		
		parser.parse("--output");
		assertEquals(parser.getBoolean("output"), true);
		
		parser.parse("-O=true");
		assertEquals(parser.getBoolean("output"), true);
		
		parser.parse("-O=1");
		assertEquals(parser.getBoolean("output"), true);
		
		parser.parse("-O 100");
		assertEquals(parser.getBoolean("output"), true);
		
	}
	
	@Test (expected = RuntimeException.class)
	public void name_first_character_is_not_number_test() {
		
		for (int i = 0; i < 10; i++) {
			String myString = Integer.toString(i) + "output";
			parser.add(myString, "o", Parser.STRING);
		}

	}
	
	@Test (expected = RuntimeException.class)
	public void shortcut_first_character_is_not_number_test() {
		
		for (int i = 0; i < 10; i++) {
			String myString = Integer.toString(i) + "o";
			parser.add("output", myString, Parser.STRING);
		}

	}
	
	@Test (expected = RuntimeException.class)
	public void name_contains_invalid_character() {
		parser.add("out%put", "o", Parser.STRING);
	}
	
	@Test
	public void name_does_not_contain_invalid_character() {
		parser.add("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890__", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890_", Parser.STRING);
	}
	
	
	//***********************************************************************
	//Here for add part 2
	//***********************************************************************
	//Test options with the same name
	//Most recent option should override previous option
	@Test
	public void no_shortcut_same_name_existing_option_test() {
		parser.add("output", "o", Parser.STRING);
		parser.add("output", "out", Parser.STRING);
		
		//Check first same name option
		parser.parse("--output=output.txt");
		assertNotEquals(parser.getString("o"), "output.txt");
		
		//Check second same name option which should override
		parser.parse("--output=output.txt");
		assertEquals(parser.getString("out"), "output.txt");
		
	}
	
	@Test
	public void no_shortcut_same_name_different_type_test() {
		parser.add("output", "o", Parser.STRING);
		parser.add("output","out", Parser.INTEGER);
		
		parser.parse("--output=output.txt");
		assertNotEquals(parser.getString("o"), "output.txt");
		
		parser.parse("--output=5");
		assertEquals(parser.getInteger("out"), 5);
		
		
		
		parser.add("output", "o", Parser.BOOLEAN);
		parser.parse("--output=5");
		assertNotEquals(parser.getInteger("out"), 5);
		
		parser.parse("--output=true");
		assertEquals(parser.getBoolean("o"), true);
		
		
		
		parser.add("output",  "out", Parser.CHAR);
		parser.parse("--output=true");
		assertNotEquals(parser.getBoolean("o"), true);
		
		parser.parse("--output=a");
		assertEquals(parser.getChar("out"), 'a');
		
		
	}
	
	@Test
	public void no_shortcut_case_sensitive_test() {
		parser.add("Output", "O", Parser.STRING);
		parser.add("output", "o", Parser.STRING);
		
		parser.parse("--Output=Output.txt");
		assertEquals(parser.getString("O"), "Output.txt");
		
		parser.parse("--output=output.txt");
		assertEquals(parser.getString("o"), "output.txt");
	}
	
	@Test
	public void no_shortcut_case_sensitive_different_types_test() {
		parser.add("Output", "O", Parser.STRING);
		parser.add("output","o", Parser.INTEGER);
		parser.add("oUtput", "o", Parser.BOOLEAN);
		parser.add("ouTput","O", Parser.CHAR);
		
		
		parser.parse("--Output=Output.txt");
		assertEquals(parser.getString("Output"), "Output.txt");
		
		parser.parse("--output=6");
		assertEquals(parser.getInteger("output"), 6);
		
		parser.parse("--oUtput=true");
		assertEquals(parser.getBoolean("oUtput"), true);
		
		parser.parse("--ouTput=a");
		assertEquals(parser.getChar("ouTput"), 'a');
		
	}
	
	@Test
	public void no_shortcut_same_name_as_shortcut_of_different_option_test() {
		parser.add("output", "o", Parser.STRING);
		parser.add("o", "shortcut", Parser.STRING);
		
		parser.parse("-o=shortcut");
		assertEquals(parser.getString("output"), "shortcut");
		
		parser.parse("--o=name");
		assertEquals(parser.getString("o"), "name");
	}
	
	@Test
	public void no_shortcut_same_name_as_shortcut_of_different_option_differnt_type_test() {
		parser.add("output", "o", Parser.STRING);
		parser.add("o", "shortcut", Parser.INTEGER);
		
		parser.parse("-o=shortcut");
		assertEquals(parser.getString("output"), "shortcut");
		
		parser.parse("--o=7");
		assertEquals(parser.getInteger("o"), 7);
	}
	
	
	@Test
	public void no_shortcut_boolean_false_case_insensitive_test() {
		parser.add("output", "shortcut", Parser.BOOLEAN);
		parser.add("output2", "shortcut2", Parser.BOOLEAN);
		
		parser.parse("--output=false");
		assertEquals(parser.getBoolean("output"), false);
		
		parser.parse("--output2=False");
		assertEquals(parser.getBoolean("output2"), false);
		
	}
	
	@Test
	public void no_shortcut_boolean_set_to_false_using_0_test() {
		parser.add("output", "shortcut", Parser.BOOLEAN);
		parser.parse("--output=0");
		assertEquals(parser.getBoolean("output"), false);
		
	}
	
	@Test
	public void no_shortcut_boolean_set_to_false_when_option_not_used_test() {
		parser.add("output", "shortcut", Parser.BOOLEAN);
		assertEquals(parser.getBoolean("output"), false);
	}
	
	public void no_shortcut_boolean_set_to_true_when_used_for_anything_else_test() {
		parser.add("output", Parser.BOOLEAN);
		parser.parse("-");
		assertEquals(parser.getBoolean("output"), true);
		
		parser.parse("--output");
		assertEquals(parser.getBoolean("output"), true);
		
		parser.parse("-O=true");
		assertEquals(parser.getBoolean("output"), true);
		
		parser.parse("-O=1");
		assertEquals(parser.getBoolean("output"), true);
		
		parser.parse("-O 100");
		assertEquals(parser.getBoolean("output"), true);
		
	}
	
	@Test (expected = RuntimeException.class)
	public void no_shortcut_name_first_character_is_not_number_test() {
		
		for (int i = 0; i < 10; i++) {
			String myString = Integer.toString(i) + "output";
			parser.add(myString, Parser.STRING);
		}

	}
	
	@Test (expected = RuntimeException.class)
	public void no_shortcut_name_contains_invalid_character() {
		parser.add("out%put", Parser.STRING);
	}
	
	@Test
	public void no_shortcut_name_does_not_contain_invalid_character() {
		parser.add("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890__", Parser.STRING);
	}

}
