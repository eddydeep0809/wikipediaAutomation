package objectRepository;

public class SearchPage {
	
	public static String search_btn						=	"//*[@content-desc='Search Wikipedia']";
	public static String search_input					=	"//*[contains(@resource-id,'search_src_text')]";
	public static String searchResults_table			=	"//*[contains(@resource-id,'search_results_list')]";
	public static String searchResults_title_txt		=	"//*[contains(@resource-id,'page_list_item_title')]";
	public static String searchResults_description_txt	=	"//*[contains(@resource-id,'page_list_item_description')]";
	public static String searhHistoryDelete_btn			=	"history_delete";
	public static String clearHistory_popup_yes_btn		=	"button1";

}
