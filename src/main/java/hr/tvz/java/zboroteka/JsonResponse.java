package hr.tvz.java.zboroteka;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class JsonResponse.
 */
@Data
@NoArgsConstructor
public class JsonResponse {

	/** The status. */
	private String status;

	/** The result. */
	private Object result;

}
