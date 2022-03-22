
// case "laptop" -> LAPTOP;
// case "desktopComputer" -> DESKTOP_COMPUTER;
// case "smartphone" -> SMARTPHONE;
// case "iPad" -> IPAD;
// case "tablet" -> TABLET;
// case "printer" -> PRINTER;
// case "monitor" -> MONITOR;
// case "hardDrive" -> HARD_DRIVE;
// case "keyboard" -> KEYBOARD;
// case "mouse" -> MOUSE;
// case "usbStick" -> USB_STICK;

const deviceNameMap = {
  laptop: 'Laptop',
  desktopComputer: 'Desktop Computer',
  smartphone: 'Smartphone',
  iPad: 'iPad',
  tablet: 'Tablet',
  printer: 'Printer',
  monitor: 'Monitor',
  hardDrive: 'Hard Drive',
  keyboard: 'Keyboard',
  mouse: 'Mouse',
  usbStick: 'USB Stick',
};

const mapDeviceTypeToName = (deviceType) => {
  return deviceNameMap[deviceType];
};

const mapDeviceNameToType = (deviceName) => {
  return Object.keys(deviceNameMap).find(key => deviceNameMap[key] === deviceName);
};

const getDeviceMap = () => {
  return deviceNameMap;
};

const DeviceService = {
  mapDeviceTypeToName,
  mapDeviceNameToType,
  getDeviceMap,
};

export default DeviceService;