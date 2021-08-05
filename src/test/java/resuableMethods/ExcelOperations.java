package resuableMethods;

import java.util.ArrayList;
import java.util.HashMap;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class ExcelOperations {

	public HashMap<String, String> getDataForExecution(String tableName) {
		String filePath							=	System.getProperty("user.dir")+"/testData/testData.xlsx";
		String query							=	"Select * From "+tableName;
		HashMap<String, String> inputParameter 	=	new HashMap<String, String>();
		ArrayList <String> columnNames			= 	new ArrayList<String>();
		Connection connection = null;
		Recordset recordSet = null;

		try {
			Fillo fillo			=	new Fillo();
			connection			= 	fillo.getConnection(filePath);
			recordSet 			=	connection.executeQuery(query);
			columnNames			= 	recordSet.getFieldNames();
			int noOfColumns			=	columnNames.size() ;

			while(recordSet.next()) {
				for(int inputParam = 0; inputParam < noOfColumns ; inputParam++ ) {
					inputParameter.put(columnNames.get(inputParam), recordSet.getField(columnNames.get(inputParam)));
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} finally {
			recordSet.close();
			connection.close();
		}
		return inputParameter;
	}
}
