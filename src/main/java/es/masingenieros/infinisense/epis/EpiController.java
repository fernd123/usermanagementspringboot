package es.masingenieros.infinisense.epis;

//@RestController
//@RequestMapping("/api/epi")
//public class EpiController {
//
//	@Autowired 
//	EpiService epiService;
//
//	@RequestMapping(method=RequestMethod.POST)
//	public ResponseEntity<?> saveEpi(@Valid @RequestBody Epi epi) throws Exception{
//		//Epi epi = createEpi(values);
//		return ResponseEntity.status(HttpStatus.CREATED)
//				.body(epiService.save(epi));
//	}
//
//	@RequestMapping(value="/{uuid}", method=RequestMethod.PUT, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//	public ResponseEntity<?> updateEpi(@PathVariable(value = "uuid") String uuid, @RequestParam Map<String, String> values) throws Exception{
//		Epi epi = createEpi(values);
//		try {
//			return ResponseEntity.status(HttpStatus.OK)
//					.body(epiService.update(uuid, epi));
//		} catch (Exception e) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//		}
//	}
//
//	@RequestMapping(value="/{uuid}", method=RequestMethod.DELETE)
//	public ResponseEntity<?> deleteEpi(@PathVariable(value = "uuid") String uuid){
//		List<String> epiIds = new ArrayList<String>();
//		epiIds.add(uuid);
//		try {
//			epiService.deleteEpisByUuid(epiIds);
//			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//		} catch (Exception e) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//		}
//	}
//
//	@RequestMapping(value="/{uuid}", method=RequestMethod.GET)
//	public ResponseEntity<?> getEpiByUuid(@PathVariable(value = "uuid") String uuid) {
//		try {
//			return ResponseEntity.status(HttpStatus.OK).body(epiService.findByUuid(uuid));
//		} catch (Exception e) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//		}
//	}
//
//
//	@GetMapping
//	public ResponseEntity<?> getAllEpis() {
//		try {
//			return ResponseEntity.status(HttpStatus.OK).body(epiService.findAll());
//		} catch (Exception e) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//		}
//	}
//
//	private Epi createEpi(Map<String, String> values) {
//		Epi epi = new Epi();
//		epi.setName(values.get("name"));
//		epi.setDescription(values.get("description"));
//		epi.setActive(Boolean.valueOf(values.get("active")));
//		return epi;
//	}
//}
