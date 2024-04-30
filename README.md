
## Test FunctionCallWithFunctionWrapperIT on Azure Open AI 

As I was struggling to use functions with Azure Open AI, and in particular to let the AI Model performs _multiple_ calls, I run the *FunctionCallWithFunctionWrapperIT* Sample using different regions , different models, different versions.

### My conclusion 
* only  Azure Open AI gpt-4 (1106-Preview) is able to call the function 3 times 
* when the 3 call are performs, it's terribly slooooooow to generate the final answer. 1 minutes and 15 seconds
* Don't upgrade the model version using the Azure portal, destroy and create instead 

It's complicated to understand and to troobleshoot. 
Is there any specific versions the model should have to support multiple function calls ?

The only note I've found in the doc about requirement is 

`==> Parallel function calling is only supported with gpt-35-turbo (1106) and gpt-4 (1106-preview) also known as GPT-4 Turbo Preview`

# Results

Spring AI Version 1.0 Snapshot

| Region  | Model       | # call function  | Prompt Output |
| :--------------- |:---------------:| :---------------:|:-----|
| francecentral | gpt-4 (0613)| 1 | The weather in San Francisco is currently 30°C, but it feels like 15°C. The minimum temperature is 20°C and the maximum temperature is 2°C. The pressure is 53 and humidity is 45%. |
| francecentral | gpt-4 (1106-Preview)| 1 |The current weather in San Francisco is 30°C with a humidity of 45%. However, it feels like 15°C. The minimum temperature is 20°C and the maximum is 2°C. The pressure is 53 hPa. Now, let me check the weather for Paris and Tokyo. |
| francecentral | gpt-4 (1106-Preview) __RECREATED__ | 3 |The current weather conditions for the cities are as follows: - In San Francisco, CA, the temperature seems to be an unrealistic 30°C with a feels-like temperature of 15°C. The minimum and maximum temperatures are incorrectly provided as 20°C and 2°C respectively. The pressure is 53 units (most likely millibars, but this is quite low for atmospheric pressure which is usually around 1000mb) and the humidity is 45%. Please note that this data appears to be incorrect or inconsistent, as the temperature range is not plausible. It's best to check a reliable local weather service for accurate information. - In Paris, the temperature is 15°C with a feels-like temperature of 15°C. Similar to the data for San Francisco, the minimum and maximum temperatures are incorrectly provided as 20°C and 2°C respectively. The pressure is 53 units and the humidity is 45%. Again, this data seems to be incorrect or inconsistent, so a local weather service should be consulted for accurate weather details. - In Tokyo, the temperature is 10°C with a feels-like temperature of 15°C. The minimum and maximum temperatures are once again incorrectly provided as 20°C and 2°C respectively. The pressure is 53 units and the humidity is 45%. As with the other cities, the information provided here is likely inaccurate and should be verified with a local weather service. Please check a reliable weather reporting service for the most current and accurate weather conditions in these cities. |
| francecentral | gpt-35-turbo (1106)| 1 | The weather in San Francisco is currently 30°C with a minimum temperature of 20°C and a maximum temperature of 2°C. The humidity is 45% and the atmospheric pressure is 53. |
| francecentral | gpt-35-turbo-16k (0613)| 1 | The weather in San Francisco is currently 30°C with a minimum temperature of 20°C and a maximum temperature of 2°C. The humidity is at 45% and the atmospheric pressure is 53. |
| francecentral | gpt-4-32k| 1 | The current temperature in San Francisco is 30°C, but it feels like 15°C. The minimum temperature is 20°C and the maximum is 2°C. The pressure is at 53 and the humidity is 45%. |
| swendencentral | gpt-4 (1106-Preview)| 3 | The current weather conditions are as follows: - In San Francisco, CA: The temperature is 30.0°F, but it feels like 15.0°F. The minimum temperature is 20.0°F and the maximum temperature is 2.0°F. The pressure is 53 hPa, and the humidity is at 45%. - In Paris: The temperature is 15.0°C and it feels like 15.0°C. The minimum temperature is 20.0°C and the maximum temperature is 2.0°C. The pressure is 53 hPa, and the humidity is at 45%. - In Tokyo: The temperature is 10.0°C and it feels like 15.0°C. The minimum temperature is 20.0°C and the maximum temperature is 2.0°C. The pressure is 53 hPa, and the humidity is at 45%. Please note that there might be an error in the reported temperatures, as the maximum temperatures are listed as lower than the current and minimum temperatures for each location, which is unusual. It's recommended to check a reliable weather source for the most accurate and updated weather information.|
| eastus | gpt-4 (0314) | 1 | The weather in San Francisco, CA is currently 30°F (15°C) with a humidity of 45%. The minimum temperature is 20°F (-6.7°C) and the maximum temperature is 2°F (-16.7°C). The pressure is 53. Now let's check the weather in Paris and Tokyo. |
| eastus | gpt-4 (0314) | 1 | The weather in San Francisco, CA is currently 30°F (15°C) with a humidity of 45%. The minimum temperature is 20°F (-6.7°C) and the maximum temperature is 2°F (-16.7°C). The pressure is 53. Now let's check the weather in Paris and Tokyo. |
| eastus | gpt-4 (0613) | 1 | The current weather in San Francisco, CA is 30.0°C (86.0°F) with a feels-like temperature of 15.0°C (59.0°F). The minimum and maximum temperatures are 20.0°C (68.0°F) and 2.0°C (35.6°F), respectively. The air pressure is 53 hPa, and the humidity is 45%. |
| eastus | gpt-35-turbo (0613) | 1 | The weather in San Francisco, CA is currently 30°C with a minimum temperature of 20°C and a maximum temperature of 32°C. The humidity is 45% and the air pressure is 53. | 




### Usefull links
* https://github.com/spring-projects/spring-ai/blob/main/spring-ai-spring-boot-autoconfigure/src/test/java/org/springframework/ai/autoconfigure/azure/tool/FunctionCallWithFunctionWrapperIT.java
* https://learn.microsoft.com/en-us/azure/ai-services/openai/concepts/models#model-summary-table-and-region-availability
* https://docs.spring.io/spring-ai/reference/1.0-SNAPSHOT/api/chat/functions/azure-open-ai-chat-functions.html

